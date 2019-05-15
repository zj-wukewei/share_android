package com.github.wkw.share.utils

import androidx.lifecycle.LiveData
import com.github.wkw.share.vo.Resource
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.plugins.RxJavaPlugins
import java.lang.ref.WeakReference


object RxJavaToLiveDataConvert {

    @JvmStatic
    fun <T> fromObservableResource(observable: Observable<T>): LiveData<Resource<T>> {
        return ObservableLiveDataResource(observable)
    }

    @JvmStatic
    fun <T> fromObservable(observable: Observable<T>): LiveData<T> {
        return ObservableLiveData(observable)
    }

    private class ObservableLiveData<T>(val observable: Observable<T>) : LiveData<T>() {

        private val mLock = Any()
        private var mDisposableRef: WeakReference<Disposable>? = null

        override fun onActive() {
            super.onActive()
            //TODO 这边是否真的需要再次建立关联
            observable.subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                    synchronized(mLock) {
                        mDisposableRef = WeakReference(d)
                    }
                }

                override fun onNext(t: T) {
                    postValue(t)
                }

                override fun onError(e: Throwable) {
                    synchronized(mLock) {
                        mDisposableRef = null
                    }
                    RxJavaPlugins.onError(OnErrorNotImplementedException(e))
                }

                override fun onComplete() {
                    synchronized(mLock) {
                        mDisposableRef = null
                    }
                }

            })
        }

        override fun onInactive() {
            super.onInactive()
            synchronized(mLock) {
                val subscriptionRef: WeakReference<Disposable>? = mDisposableRef
                val subscription = subscriptionRef?.get()
                subscription?.dispose()
                mDisposableRef = null
            }

        }
    }


    private class ObservableLiveDataResource<T>(val observable: Observable<T>) : LiveData<Resource<T>>() {

        private val mLock = Any()
        private var mDisposableRef: WeakReference<Disposable>? = null

        override fun onActive() {
            super.onActive()
            observable.subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                    synchronized(mLock) {
                        mDisposableRef = WeakReference(d)
                    }
                    postValue(Resource.loading(null))
                }

                override fun onNext(t: T) {
                    postValue(Resource.success(t))
                }

                override fun onError(e: Throwable) {
                    synchronized(mLock) {
                        mDisposableRef = null
                    }
                    postValue(Resource.error(e, null))
                }

                override fun onComplete() {
                    synchronized(mLock) {
                        mDisposableRef = null
                    }
                }

            })
        }

        override fun onInactive() {
            super.onInactive()
            synchronized(mLock) {
                val subscriptionRef: WeakReference<Disposable>? = mDisposableRef
                val subscription = subscriptionRef?.get()
                subscription?.dispose()
                mDisposableRef = null
            }

        }
    }


}