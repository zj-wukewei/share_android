package com.github.wkw.share.vo

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

data class Feed(val id: Int,
                val userId: String,
                val content: String,
                val video: String?,
                val commentCount: Int = 0,
                val tagId: Int?,
                val categoryId: Int?,
                val userName: String?,
                val userAvatar: String?,
                val addTime: String?,
                val images: String?) : BaseObservable() {
    @Bindable
    var liked = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.liked)
        }

    @Bindable
    var likeCount = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.likeCount)
        }
}