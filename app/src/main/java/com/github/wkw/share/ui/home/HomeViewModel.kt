package com.github.wkw.share.ui.home

import android.arch.lifecycle.ViewModel
import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.request.AbstractQry
import com.github.wkw.share.repository.FeedRepository
import com.github.wkw.share.vo.Feed
import io.reactivex.Observable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val feedRepository: FeedRepository) : ViewModel() {

    fun feeds(query: String): Observable<ListDataEntity<Feed>> {
        return feedRepository.feeds(AbstractQry())
    }
}