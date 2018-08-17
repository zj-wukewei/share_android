package com.github.wkw.share.ui.extens

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import com.github.wkw.share.vo.Resource
import com.github.wkw.share.vo.Status
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