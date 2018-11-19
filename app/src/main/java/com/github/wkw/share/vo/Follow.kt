package com.github.wkw.share.vo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

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