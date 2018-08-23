package com.github.wkw.share.vo

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

data class Follow(
        val id: Int,
        val userId: Int,
        val nickname: String?,
        val avatar: String?
) : BaseObservable() {
    @Bindable
    var followed = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.followed)
        }
}