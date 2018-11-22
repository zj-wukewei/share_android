package com.github.wkw.share

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AppExecutors(
        val diskIO: Executor,
        val networkIO: Scheduler,
        val mainThread: Scheduler
) {

    @Inject
    constructor() : this(
            Executors.newSingleThreadExecutor(),
            Schedulers.io(),
            AndroidSchedulers.mainThread()
    )

    fun diskIO(): Executor {
        return diskIO
    }

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