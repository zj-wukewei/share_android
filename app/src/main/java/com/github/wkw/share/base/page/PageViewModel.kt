package com.github.wkw.share.base.page

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.utils.ext.toFlowable
import com.github.wkw.share.viewmodel.AutoDisposeViewModel
import com.github.wkw.share.vo.Status
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable
import timber.log.Timber

abstract class PageViewModel<T> : AutoDisposeViewModel() {
    val isRefreshing = MutableLiveData<Boolean>()
    val status = MutableLiveData<Status>()

    val empty = MutableLiveData<Boolean>()
    val results = MutableLiveData<PagedList<T>>()


    fun initDataRepository() {
        Paging
                .dataSource { pageNumber ->
                    when (pageNumber) {
                        1 -> queryFeedDataSourceRefresh()
                        else -> queryFeedDataSource(pageNumber)
                    }

                }.toFlowable()
                .autoDisposable(this)
                .subscribeBy {
                    results.postValue(it)
                }
    }

    fun onRefreshEvent() {
        results.value?.dataSource?.invalidate()
    }

    private fun queryFeedDataSourceRefresh() = queryFeedDataSource(1)
            .doOnSubscribe {
                this@PageViewModel.isRefreshing.postValue(true)
                this@PageViewModel.status.postValue(Status.LOADING)

            }
            .doFinally {
                this@PageViewModel.isRefreshing.postValue(false)
            }
            .doOnNext {
                this.empty.postValue(it.isEmpty())
            }

    abstract fun createRemoteDataSource(pageNumber: Int): Flowable<List<T>>

    private fun queryFeedDataSource(pageNumber: Int) = createRemoteDataSource(pageNumber)
            .doOnError {
                this@PageViewModel.status.postValue(Status.ERROR)
            }
            .doOnNext {
                this@PageViewModel.status.postValue(Status.SUCCESS)
            }
}