package com.github.wkw.share.repository

import com.github.wkw.share.AppExecutors
import com.github.wkw.share.api.ShareService
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.api.request.transformUserInfo
import com.github.wkw.share.db.FollowDao
import com.github.wkw.share.vo.Follow
import com.github.wkw.share.vo.UserInfo
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val shareService: ShareService,
                                         private val followDao: FollowDao,
                                         private val appExecutors: AppExecutors) {

    /***
     * 登录
     * @author GoGo
     * @param
     * @return
     */
    fun login(loginRequest: LoginRequest): Observable<UserEntity> {
        return shareService.login(loginRequest)
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }

    /***
     * 用户基本信息
     * @author GoGo
     * @param
     * @return
     */
    fun userInfo(): Observable<UserInfo> {
        return shareService.userInfo()
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }

    /***
     *同步关注列表
     * @author GoGo
     * @param
     * @return
     */
    fun syncRemoteFollows(): Observable<List<Follow>> {
        return shareService.myFollows()
                .compose(RepositoryUtils.handleResult())
                .subscribeOn(appExecutors.networkIO)
    }

    /***
     * 获取本地列表
     * @author GoGo
     * @param
     * @return
     */
    fun getLocalFollows(): Flowable<List<Follow>> = followDao.getAll()

    /***
     * 插入本地关系列表
     * @author GoGo
     * @param
     * @return
     */
    fun insertFollowsAll(follows: List<Follow>) {
        followDao.deleteAllFollows()
        followDao.insertAll(follows)
    }

    /***
     * 获取远程粉丝
     * @author GoGo
     * @param
     * @return
     */
    fun myFans(): Observable<List<Follow>> {
        return shareService.myFans()
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }

    /***
     * 获取关注列表
     * @author GoGo
     * @param
     * @return
     */
    fun follow(item: Follow): Observable<Boolean> {
        return shareService.follow(item.userId.toString())
                .compose(RepositoryUtils.handleResult())
                .doOnNext { it -> if (!it) followDao.delete(item) }
                .compose(appExecutors.ioMainScheduler())
    }

    fun updateInfo(userInfo: UserInfo): Observable<Nothing> {
        return shareService.postUserInfo(transformUserInfo(userInfo))
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }
}