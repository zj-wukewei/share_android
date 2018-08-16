package com.github.wkw.share.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.request.FeedQry
import com.github.wkw.share.extens.asLiveData
import com.github.wkw.share.repository.FeedRepository
import com.github.wkw.share.ui.page.PageViewModel
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Resource
import com.github.wkw.share.vo.Status
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val feedRepository: FeedRepository) : PageViewModel() {
    val responseResult = MediatorLiveData<List<Feed>>()

    private val query = MutableLiveData<FeedQry>()
    var feedQry = FeedQry()

    private val results: LiveData<Resource<ListDataEntity<Feed>>> = Transformations
            .switchMap(query) { search ->
                feedRepository.feeds(search)
                        .asLiveData()
            }

    init {
        responseResult.addSource(results) { it ->
            it?.let {
                if (it.status == Status.SUCCESS) {
                    empty.value = isRefreshing.value == true && it.data?.list?.isEmpty() ?: false
                    hasMore.postValue(it.data?.hasMore)
                    responseResult.postValue(it.data?.list)
                }

                if (it.status != Status.LOADING) {
                    isRefreshing.value = false
                }
                status.postValue(it.status)
            }
        }
    }

    override fun onSwipeRefresh() {
        isRefreshing.value = true
        feedQry.pageNum = getNextPn()
        query.postValue(feedQry)
    }

    override fun loadMore() {
        feedQry.pageNum = getNextPn()
        query.postValue(feedQry)
    }
}