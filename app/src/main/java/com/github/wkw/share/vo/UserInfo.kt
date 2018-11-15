package com.github.wkw.share.vo

data class UserInfo(
        val id: Int,
        val userId: Int,
        val nickname: String?,
        val avatar: String?,
        val bio: String?,
        val location: String?,
        val age: Int?,
        val gender: Int?,
        val categoryId: Int?
)