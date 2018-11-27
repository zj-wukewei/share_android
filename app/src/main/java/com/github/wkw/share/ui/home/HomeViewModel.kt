package com.github.wkw.share.ui.home

import com.github.wkw.share.api.request.FeedQry
import com.github.wkw.share.base.page.PageViewModel
import com.github.wkw.share.repository.FeedRepository
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Like
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val feedRepository: FeedRepository) : PageViewModel<Feed>() {


    var feedQry = FeedQry()

    override fun createRemoteDataSource(pageNumber: Int): Flowable<List<Feed>> {
        feedQry.pageNum = pageNumber
        return feedRepository.feeds(feedQry)
    }

    fun like(feedId: Int): Observable<Like> = feedRepository.like(feedId)

}