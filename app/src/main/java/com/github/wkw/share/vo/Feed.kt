package com.github.wkw.share.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

@Entity
data class Feed(@PrimaryKey val id: Int,
                @ColumnInfo(name = "userId") val userId: String,
                @ColumnInfo(name = "content") val content: String,
                @ColumnInfo(name = "video") val video: String?,
                @ColumnInfo(name = "commentCount") val commentCount: Int = 0,
                @ColumnInfo(name = "tagId") val tagId: Int?,
                @ColumnInfo(name = "categoryId") val categoryId: Int?,
                @ColumnInfo(name = "userName") val userName: String?,
                @ColumnInfo(name = "userAvatar") val userAvatar: String?,
                @ColumnInfo(name = "addTime") val addTime: String?,
                @ColumnInfo(name = "images") val images: String?) : BaseObservable() {

    @ColumnInfo(name = "liked")
    @Bindable
    var liked = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.liked)
        }

    @ColumnInfo(name = "likeCount")
    @Bindable
    var likeCount = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.likeCount)
        }
}