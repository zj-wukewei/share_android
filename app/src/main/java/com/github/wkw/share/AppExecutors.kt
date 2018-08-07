package com.github.wkw.share

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AppExecutors(
        val networkIO: Scheduler,
        val mainThread: Scheduler
) {

    @Inject
    constructor() : this(
            Schedulers.io(),
            AndroidSchedulers.mainThread()
    )

    fun <T> ioMainScheduler(): IoMainScheduler<T> {
        return IoMainScheduler(this@AppExecutors)
    }

    class IoMainScheduler<T>(private val appExecutors: AppExecutors) : ObservableTransformer<T, T> {
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream.subscribeOn(appExecutors.networkIO)
                    .observeOn(appExecutors.mainThread)
        }
    }
}