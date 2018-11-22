package com.github.wkw.share.vo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

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