package com.github.wkw.share.vo

import java.util.*

data class Feed(val id: Int,
                val userId: String,
                val content: String,
                val video: String?,
                val likeCount: Int = 0,
                val commentCount: Int = 0,
                val tagId: Int?,
                val categoryId: Int?,
                val userName: String?,
                val userAvatar: String?,
                val addTime: Date?,
                val images: String?)