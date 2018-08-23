package com.github.wkw.share.repository

import com.github.wkw.share.AppExecutors
import com.github.wkw.share.api.ShareResponse
import com.github.wkw.share.api.ShareService
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.utils.encode
import com.github.wkw.share.vo.Follow
import com.github.wkw.share.vo.UserInfo
import io.reactivex.Observable
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val shareService: ShareService, private val appExecutors: AppExecutors) {

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


    fun myFollows(): Observable<List<Follow>> {
        return shareService.myFollows()
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())

    }


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