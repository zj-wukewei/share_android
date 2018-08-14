package com.github.wkw.share.ui.page

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.vo.Resource
import com.github.wkw.share.vo.Status

abstract class PageViewModel<T> : ViewModel() {
    val isRefreshing = MutableLiveData<Boolean>()
    val status = MutableLiveData<Status>()
    protected var pn = 1

    abstract fun loadData(isRefreshing: Boolean): LiveData<Resource<ListDataEntity<T>>>


}