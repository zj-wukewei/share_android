package com.github.wkw.share.api

import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.reponse.UserEntity
import com.github.wkw.share.api.request.FeedQry
import com.github.wkw.share.api.request.LoginRequest
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Follow
import com.github.wkw.share.vo.Like
import com.github.wkw.share.vo.UserInfo
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author GoGo on 2018/8/6.
 */
interface ShareService {
    /***
     * 登录接口
     *
     * @param
     * @return
     */
    @POST("user/login")
    fun login(@Body login: LoginRequest): Observable<ShareResponse<UserEntity>>


    /***
     *  首页feed列表
     *
     * @param
     * @return
     */
    @POST("feed/list")
    fun feeds(@Body qry: FeedQry): Observable<ShareResponse<ListDataEntity<Feed>>>

    @GET("feed/like/{feedId}")
    fun likeFeed(@Path("feedId") feedId: Int): Observable<ShareResponse<Like>>

    /***
     *  用户信息
     *
     * @param
     * @return
     */
    @GET("user/info")
    fun userInfo(): Observable<ShareResponse<UserInfo>>

    /***
     *  我关注的人
     *
     * @param
     * @return
     */
    @GET("user/follows")
    fun myFollows(): Observable<ShareResponse<List<Follow>>>


    /***
     *  我的粉丝
     *
     * @param
     * @return
     */
    @GET("user/follows")
    fun myFans(): Observable<ShareResponse<List<Follow>>>


    /***
     *  关注接口
     *
     * @param   userId 关注者的id
     * @return
     */
    @GET("user/follow/{userId}")
    fun follow(@Path("userId") userId: String): Observable<ShareResponse<Boolean>>
}