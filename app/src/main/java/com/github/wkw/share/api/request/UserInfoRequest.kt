package com.github.wkw.share.api.request

data class UserInfoRequest(
        val nickname: String?,
        val avatar: String?,
        val bio: String?,
        val gender: Int?,
        val categoryId: Int
)