package com.github.wkw.share.api

import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.request.AbstractQry
import com.github.wkw.share.api.request.FeedQry
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Follow
import com.github.wkw.share.vo.UserInfo
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author GoGo on 2018/8/6.
 */
interface ShareService {
    @POST("user/login")
    fun login(@Body login: LoginRequest): Observable<ShareResponse<UserEntity>>


    @POST("feed/list")
    fun feeds(@Body qry: FeedQry): Observable<ShareResponse<ListDataEntity<Feed>>>

    @GET("user/info")
    fun userInfo(): Observable<ShareResponse<UserInfo>>

    @GET("user/follows")
    fun myFollows(): Observable<ShareResponse<List<Follow>>>


    @GET("user/follows")
    fun myFans(): Observable<ShareResponse<List<Follow>>>
}