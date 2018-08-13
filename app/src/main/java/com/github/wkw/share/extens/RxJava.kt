package com.github.wkw.share.extens

import android.arch.lifecycle.LiveData
import com.github.wkw.share.utils.RxJavaToLiveDataConvert.fromObservable
import com.github.wkw.share.vo.Resource
import io.reactivex.Observable

inline fun <T> Observable<T>.asLiveData(): LiveData<Resource<T>> = fromObservable(this)