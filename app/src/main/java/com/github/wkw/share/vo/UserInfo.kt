package com.github.wkw.share.vo

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.github.wkw.share.BR

data class UserInfo(
        val id: Int,
        val userId: Int
) : BaseObservable() {
    @Bindable
    var nickname: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.nickname)
        }
    @Bindable
    var avatar: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.avatar)
        }

    @Bindable
    var bio: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bio)
        }


    @Bindable
    var location: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.location)
        }


    @Bindable
    var age: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.age)
        }

    @Bindable
    var gender: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.gender)
        }

    @Bindable
    var categoryId: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.categoryId)
        }
}