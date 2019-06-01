package com.github.wkw.share.api.request

import com.github.wkw.share.vo.UserInfo

data class UserInfoRequest(
        val nickname: String?,
        val avatar: String?,
        val bio: String?,
        val location: String?,
        val age: Int?,
        val gender: Int?,
        val categoryId: Int?
)

fun transformUserInfo(userInfo: UserInfo): UserInfoRequest = UserInfoRequest(
        userInfo.nickname,
        userInfo.avatar,
        userInfo.bio,
        userInfo.location,
        userInfo.age,
        userInfo.gender,
        userInfo.categoryId
)
