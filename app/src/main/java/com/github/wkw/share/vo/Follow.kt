package com.github.wkw.share.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

@Entity
data class Follow(
        val id: Int,
        @PrimaryKey val userId: Int,
        @ColumnInfo(name = "nickname") val nickname: String?,
        @ColumnInfo(name = "avatar") val avatar: String?
) : BaseObservable() {
    @ColumnInfo(name = "followed")
    @Bindable
    var followed = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.followed)
        }
}