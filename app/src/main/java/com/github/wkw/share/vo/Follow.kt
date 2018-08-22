package com.github.wkw.share.vo

data class Follow(
        val id: Int,
        val userId: Int,
        val nickname: String?,
        val avatar: String?,
        val followed: Boolean?
)