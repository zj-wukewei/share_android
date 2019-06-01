package com.github.wkw.share.repository

import com.github.wkw.share.api.ShareResponse
import com.github.wkw.share.api.exception.NetworkConnectionException
import com.github.wkw.share.api.exception.ResponseException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class RepositoryUtils {
    companion object {
        private val TAG = "RepositoryUtils"
        private val DEFAULT_ERROR_MSG = "Unknown error"
        private val SUCCESS_CODE = 0
        fun <T> handleResult(): ObservableTransformer<ShareResponse<T>, T> {
            return ObservableTransformer { it ->
                it.flatMap {
                    when (SUCCESS_CODE) {
                        it.code -> createData(it.data)
                        else -> Observable.error<T>(ResponseException(it))
                    }
                }
            }
        }


        private fun <T> createData(t: T?): Observable<T> {
            return Observable.create { observable ->
                try {
                    t?.let {
                        observable.onNext(t!!)
                    }
                    observable.onComplete()
                } catch (e: Exception) {
                    observable.onError(e)
                }
            }
        }
    }
}