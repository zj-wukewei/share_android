package com.github.wkw.share.api

import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.LoginRequest
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author GoGo on 2018/8/6.
 */
interface ShareService {
    @POST("user/login")
    fun login(@Body login: LoginRequest): Observable<ShareResponse<UserEntity>>
}