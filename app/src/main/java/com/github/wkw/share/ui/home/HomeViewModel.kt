package com.github.wkw.share.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.request.AbstractQry
import com.github.wkw.share.extens.asLiveData
import com.github.wkw.share.repository.FeedRepository
import com.github.wkw.share.ui.page.PageViewModel
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Resource
import io.reactivex.Observable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val feedRepository: FeedRepository) : PageViewModel<Feed>() {

    private val query = MutableLiveData<String>()

    val results: LiveData<Resource<ListDataEntity<Feed>>> = Transformations
            .switchMap(query) { search ->
                feedRepository.feeds(AbstractQry())
                        .asLiveData()
            }

    override fun loadData(isRefreshing: Boolean): LiveData<Resource<ListDataEntity<Feed>>> {
        return feedRepository.feeds(AbstractQry())
                .asLiveData()
    }

    fun feeds(query: String): Observable<ListDataEntity<Feed>> {
        return feedRepository.feeds(AbstractQry())
    }
}