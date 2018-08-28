package com.github.wkw.share.ui.home

import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.request.FeedQry
import com.github.wkw.share.repository.FeedRepository
import com.github.wkw.share.ui.page.PageViewModel
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Like
import io.reactivex.Observable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val feedRepository: FeedRepository) : PageViewModel<FeedQry, Feed>() {

    var feedQry = FeedQry()

    override fun input() = feedQry

    override fun transformer(query: Observable<FeedQry>): Observable<ListDataEntity<Feed>> {
        return query.flatMap { feedRepository.feeds(it) }
    }


    fun like(feedId: Int): Observable<Like> = feedRepository.like(feedId)

}