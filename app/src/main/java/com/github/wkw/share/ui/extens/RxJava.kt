package com.github.wkw.share.ui.extens

import android.arch.lifecycle.LiveData
import com.github.wkw.share.utils.RxJavaToLiveDataConvert.fromObservable
import com.github.wkw.share.utils.RxJavaToLiveDataConvert.fromObservableResource
import com.github.wkw.share.vo.Resource
import io.reactivex.Observable

inline fun <T> Observable<T>.asResourceLiveData(): LiveData<Resource<T>> = fromObservableResource(this)
inline fun <T> Observable<T>.asLiveData(): LiveData<T> = fromObservable(this)