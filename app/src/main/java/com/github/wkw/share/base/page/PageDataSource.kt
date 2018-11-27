package com.github.wkw.share.base.page

import android.annotation.SuppressLint
import android.arch.paging.PageKeyedDataSource
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy

/**
 * @author GoGo on 2018-11-27.
 */
@SuppressLint("CheckResult")
class PageDataSource<T>(private val remoteSourceProvider: (Int) -> Flowable<List<T>>): PageKeyedDataSource<Int, T>() {

    private var pageNumber = 1


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        pageNumber = 1
        remoteSourceProvider(pageNumber)
                .subscribeBy {
                    callback.onResult(it, pageNumber, pageNumber + 1)
                    pageNumber ++
                }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        remoteSourceProvider(pageNumber)
                .subscribeBy {
                    callback.onResult(it, pageNumber + 1)
                    pageNumber ++
                }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
    }
}