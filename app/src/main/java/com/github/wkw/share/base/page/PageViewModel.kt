package com.github.wkw.share.base.page

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.viewmodel.AutoDisposeViewModel
import com.github.wkw.share.vo.Status
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable

abstract class PageViewModel<T> : AutoDisposeViewModel() {
    val isRefreshing = MutableLiveData<Boolean>()
    val status = MutableLiveData<Status>()

    val empty = MutableLiveData<Boolean>()
    val results = MutableLiveData<PagedList<T>>()


    fun initDataRepository() {
        Paging
                .dataSource { pageNumber ->
                    when (pageNumber) {
                        1 -> queryPageDataSourceRefresh()
                        else -> queryPageDataSource(pageNumber)
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

    private fun queryPageDataSourceRefresh() = queryPageDataSource(1)
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

    private fun queryPageDataSource(pageNumber: Int) = createRemoteDataSource(pageNumber)
            .doOnError {
                this@PageViewModel.status.postValue(Status.ERROR)
            }
            .doOnNext {
                this@PageViewModel.status.postValue(Status.SUCCESS)
            }
}