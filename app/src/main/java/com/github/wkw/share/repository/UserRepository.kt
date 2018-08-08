package com.github.wkw.share.repository

import com.github.wkw.share.AppExecutors
import com.github.wkw.share.api.ShareService
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.utils.encode
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val shareService: ShareService, private val appExecutors: AppExecutors) {

    fun login(loginRequest: LoginRequest): Observable<UserEntity> {
        return Observable.just(loginRequest)
                .flatMap {
                    val mdLogin = LoginRequest()
                    mdLogin.mobile = loginRequest.mobile
                    mdLogin.password = encode(loginRequest.password)
                    Observable.just(mdLogin)
                }
                .flatMap { shareService.login(it) }
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())


    }
}