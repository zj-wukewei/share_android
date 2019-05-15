package com.github.wkw.share.utils.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.github.wkw.share.vo.Resource
import com.github.wkw.share.vo.Status
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.MainThreadDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.plugins.RxJavaPlugins

private val onLoadingSub: (Boolean) -> Unit = {}
private val onErrorStub: (Throwable) -> Unit = {
    RxJavaPlugins.onError(OnErrorNotImplementedException(it))
}
private val onSuccessStub: (Any?) -> Unit = {}


fun <T : Any> LiveData<Resource<T>>.observeBy(owner: LifecycleOwner,
                                              onLoading: (Boolean) -> Unit = onLoadingSub,
                                              onSuccess: (T?) -> Unit = onSuccessStub,
                                              onError: (Throwable) -> Unit = onErrorStub
) = observe(owner, Observer { it ->
    it?.let {
        when (it.status) {
            Status.LOADING -> onLoading(true)
            Status.ERROR -> {
                it.throwable?.let { error ->
                    onError(error)
                }
                onLoading(false)
            }
            Status.SUCCESS -> {
                onSuccess(it.data)
                onLoading(false)
            }
        }
    }
})


fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}

fun <T> LiveData<T>.toFlowable(): Flowable<T> =
        Flowable.create({ emitter ->
            val observer = Observer<T> {
                it?.let { emitter.onNext(it) }
            }
            observeForever(observer)

            emitter.setCancellable {
                object : MainThreadDisposable() {
                    override fun onDispose() = removeObserver(observer)
                }
            }

        }, BackpressureStrategy.LATEST)


fun <T> LiveData<T>.toObservable(): Observable<T> =
        Observable.create { emitter ->
            val observer = Observer<T> { it ->
                it?.let { emitter.onNext(it) }
            }
            observeForever(observer)

            emitter.setCancellable {
                object : MainThreadDisposable() {
                    override fun onDispose() = removeObserver(observer)
                }
            }

        }