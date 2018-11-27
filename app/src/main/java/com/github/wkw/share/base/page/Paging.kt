package com.github.wkw.share.base.page

import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * @author GoGo on 2018-11-27.
 */
object Paging {
    fun <T> dataSource(remoteSourceProvider: (Int) -> Flowable<List<T>>): DataSource.Factory<Int, T> =
            object : DataSource.Factory<Int, T>() {
                override fun create(): DataSource<Int, T> =
                        PageDataSource(remoteSourceProvider)
            }
}

fun <T> DataSource.Factory<Int, T>.toFlowable(
            enablePlaceholders: Boolean = false,
            pageSize: Int = 20,
            initialLoadSizeHint: Int = 20,
            prefetchDistance: Int = 10
    ): Flowable<PagedList<T>> = RxPagedListBuilder<Int, T>(this, PagedList.Config
            .Builder()
            .setInitialLoadSizeHint(initialLoadSizeHint)
            .setPageSize(pageSize)
            .setPrefetchDistance(prefetchDistance)
            .setEnablePlaceholders(enablePlaceholders)
            .build()
    ).buildFlowable(BackpressureStrategy.LATEST)
