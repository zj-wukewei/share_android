package com.github.wkw.share.repository

import com.github.wkw.share.AppExecutors
import com.github.wkw.share.api.ShareService
import com.github.wkw.share.api.reponse.ListDataEntity
import com.github.wkw.share.api.request.AbstractQry
import com.github.wkw.share.vo.Feed
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepository @Inject constructor(private val shareService: ShareService, private val appExecutors: AppExecutors) {

    fun feeds(qry: AbstractQry): Observable<ListDataEntity<Feed>> {
        return shareService.feeds(qry)
                .compose(appExecutors.ioMainScheduler())
                .compose(RepositoryUtils.handleResult())
    }
}