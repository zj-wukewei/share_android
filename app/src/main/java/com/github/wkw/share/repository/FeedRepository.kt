package com.github.wkw.share.repository

import com.github.wkw.share.AppExecutors
import com.github.wkw.share.api.ShareService
import com.github.wkw.share.api.request.FeedQry
import com.github.wkw.share.db.FeedDao
import com.github.wkw.share.vo.Feed
import com.github.wkw.share.vo.Like
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepository @Inject constructor(private val shareService: ShareService,
                                         private val feedDao: FeedDao,
                                         private val appExecutors: AppExecutors) {

    /***
     * 获取首页feed列表
     * @author GoGo
     * @param
     * @return
     */
    fun feeds(qry: FeedQry): Flowable<List<Feed>> {
        return shareService.feeds(qry)
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
                .flatMap {
                    Observable.just(it.list)
                }
                .toFlowable(BackpressureStrategy.LATEST)
    }

    /***
     * feed的喜欢
     * @author GoGo
     * @param
     * @return
     */
    fun like(feedId: Int): Observable<Like> {
        return shareService.likeFeed(feedId)
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }

    fun feedDetail(feedId: Int): Flowable<Feed> {
        val local = feedDao.selectByFeedId(feedId)
                .subscribeOn(appExecutors.networkIO)
                .observeOn(appExecutors.mainThread)


        val remote = shareService.feedDetail(feedId)
                .subscribeOn(appExecutors.networkIO)
                .compose(RepositoryUtils.handleResult())
                .doOnNext { feedDao.insert(it) }
                .observeOn(appExecutors.mainThread)


        return Flowable.merge(local.toFlowable(), remote.toFlowable(BackpressureStrategy.BUFFER))
    }
}