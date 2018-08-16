package com.github.wkw.share.ui.page

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.wkw.share.vo.Status
import timber.log.Timber

abstract class PageViewModel : ViewModel() {
    val isRefreshing = MutableLiveData<Boolean>()
    val status = MutableLiveData<Status>()
    val hasMore = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()

    init {
        empty.value = false
        hasMore.value = true
    }

    private var pn = 1

    abstract fun loadMore()


    abstract fun onSwipeRefresh()

    fun getNextPn(): Int {
        Timber.d(isRefreshing.value.toString())
        return if (isRefreshing.value == true) {
            pn = 1
            pn
        } else {
            pn++
            pn
        }
    }

}