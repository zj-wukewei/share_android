package com.github.wkw.share.vo

data class LikeEvent(val userId: Int,
                     val feedId: Int,
                     val nickName: String,
                     val content: String
)