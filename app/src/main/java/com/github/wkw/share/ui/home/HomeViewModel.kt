package com.github.wkw.share.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.request.AbstractQry
import com.github.wkw.share.extens.asLiveData
import com.github.wkw.share.repository.FeedRepository
import com.github.wkw.share.ui.page.PageViewModel
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Resource
import com.github.wkw.share.vo.Status
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val feedRepository: FeedRepository) : PageViewModel() {
    val responseResult = MediatorLiveData<List<Feed>>()

    private val query = MutableLiveData<String>()

    private val results: LiveData<Resource<ListDataEntity<Feed>>> = Transformations
            .switchMap(query) { search ->
                feedRepository.feeds(AbstractQry())
                        .asLiveData()
            }

    init {
        responseResult.addSource(results) { it ->
            isRefreshing.postValue(false)
            status.postValue(it?.status)
            if (it?.status == Status.SUCCESS) {
                hasMore.postValue(it.data?.hasMore)
                responseResult.postValue(it.data?.list)
            }
        }
    }

    override fun onSwipeRefresh() {
        isRefreshing.postValue(true)
        query.postValue("")
    }

    override fun loadMore() {
        query.postValue("")
    }
}