package com.github.wkw.share.ui.page

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.wkw.share.vo.Status

abstract class PageViewModel : ViewModel() {
    val isRefreshing = MutableLiveData<Boolean>()
    val status = MutableLiveData<Status>()
    val hasMore = MutableLiveData<Boolean>()


    protected var pn = 1

    abstract fun loadMore()


    abstract fun onSwipeRefresh()

}