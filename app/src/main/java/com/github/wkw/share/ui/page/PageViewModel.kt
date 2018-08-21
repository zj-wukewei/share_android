package com.github.wkw.share.ui.page

import android.arch.lifecycle.MutableLiveData
import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.request.AbstractQry
import com.github.wkw.share.ui.extens.toObservable
import com.github.wkw.share.viewmodel.BaseViewModel
import com.github.wkw.share.vo.Status
import io.reactivex.Observable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

abstract class PageViewModel<Query : AbstractQry, T> : BaseViewModel() {
    val isRefreshing = MutableLiveData<Boolean>()
    val status = MutableLiveData<Status>()
    val hasMore = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()

    val query = MutableLiveData<Query>()
    val results = MutableLiveData<List<T>>()

    init {
        query.toObservable()
                .compose {
                    transformer(it)
                }
                .subscribeBy(onNext = { it ->
                    it?.let {
                        hasMore.postValue(it.hasMore)
                        empty.postValue(isRefreshing.value == true && it.list.isEmpty())
                        if (isRefreshing.value == true) {
                            results.postValue(it.list)
                        } else {
                            val list = results.value?.plus(it.list) ?: it.list
                            results.postValue(list)
                        }
                    }
                    isRefreshing.postValue(false)
                }, onError = {
                    RxJavaPlugins.onError(OnErrorNotImplementedException(it))
                    status.postValue(Status.ERROR)
                    isRefreshing.postValue(false)
                })
                .addTo(disposable)
    }

    private var pn = 1

    fun loadMore() {
        val newQuery = input()
        newQuery.pageNum = getNextPn()
        query.postValue(newQuery)
    }

    abstract fun transformer(query: Observable<Query>): Observable<ListDataEntity<T>>

    abstract fun input(): Query

    fun onSwipeRefresh() {
        val newQuery = input()
        isRefreshing.value = true
        newQuery.pageNum = getNextPn()
        query.postValue(newQuery)
    }

    private fun getNextPn(): Int {
        return if (isRefreshing.value == true) {
            pn = 1
            pn
        } else {
            pn++
            pn
        }
    }

}