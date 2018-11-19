package com.github.wkw.share.repository

import android.annotation.SuppressLint
import com.github.wkw.share.AppExecutors
import com.github.wkw.share.api.ShareService
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.db.FollowDao
import com.github.wkw.share.vo.Follow
import com.github.wkw.share.vo.UserInfo
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val shareService: ShareService,
                                         private val followDao: FollowDao,
                                         private val appExecutors: AppExecutors) {

    fun login(loginRequest: LoginRequest): Observable<UserEntity> {
        return shareService.login(loginRequest)
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }

    fun userInfo(): Observable<UserInfo> {
        return shareService.userInfo()
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }


    fun syncRemoteFollows(): Observable<List<Follow>> {
        return shareService.myFollows()
                .compose(RepositoryUtils.handleResult())
                .subscribeOn(appExecutors.networkIO)
    }

    fun getLocalFollows(): Flowable<List<Follow>> = followDao.getAll()

    fun insertFollowsAll(follows: List<Follow>) = followDao.insertAll(follows)

    fun myFans(): Observable<List<Follow>> {
        return shareService.myFans()
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }

    fun follow(userId: String): Observable<Boolean> {
        return shareService.follow(userId)
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }
}