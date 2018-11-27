package com.github.wkw.share.ui.home

import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.request.FeedQry
import com.github.wkw.share.base.page.PageViewModel
import com.github.wkw.share.base.page.Paging
import com.github.wkw.share.base.page.toFlowable
import com.github.wkw.share.repository.FeedRepository
import com.github.wkw.share.utils.ext.subscribeBy
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Like
import com.github.wkw.share.vo.Status
import com.uber.autodispose.autoDisposable
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val feedRepository: FeedRepository) : PageViewModel<FeedQry, Feed>() {

    var feedQry = FeedQry()

    override fun input() = feedQry

    override fun transformer(query: Observable<FeedQry>): Observable<ListDataEntity<Feed>> {
        return query.flatMap { feedRepository.feeds(it) }
    }


    fun initFeedRepository() {
        Paging
                .dataSource { pageNumber ->
                    when (pageNumber) {
                        1 -> queryFeedDataSourceRefresh()
                        else -> queryFeedDataSource(pageNumber)
                    }
                }
                .toFlowable()
                .autoDisposable(this)
                .subscribeBy {

                }

    }

    private fun queryFeedDataSource(pageNumber: Int): Flowable<List<Feed>> {
        feedQry.pageNum = pageNumber
        return feedRepository.feeds(feedQry)
                .flatMap {
                    Observable.just(it.list)
                }
                .doOnError {
                    this@HomeViewModel.status.postValue(Status.ERROR)
                }
                .doOnNext {
                    this@HomeViewModel.status.postValue(Status.SUCCESS)
                }
                .toFlowable(BackpressureStrategy.LATEST)

    }

    private fun queryFeedDataSourceRefresh(): Flowable<List<Feed>> =
            queryFeedDataSource(1)
                    .startWith {
                        this@HomeViewModel.isRefreshing.postValue(true)
                        this@HomeViewModel.status.postValue(Status.LOADING)

                    }
                    .doFinally {
                        this@HomeViewModel.isRefreshing.postValue(false)
                    }
                    .doOnNext {
                        it.isEmpty().let {
                            this.empty.postValue(true)
                        }
                    }


    fun like(feedId: Int): Observable<Like> = feedRepository.like(feedId)

}