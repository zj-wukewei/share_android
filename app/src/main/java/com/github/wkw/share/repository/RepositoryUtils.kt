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
            return ObservableTransformer {
                it.flatMap {
                    if (it == null) {
                        Observable.error(NetworkConnectionException())
                    } else if (it.code == SUCCESS_CODE) {
                        createData(it.data)
                    } else {
                        Observable.error<T>(ResponseException(it))
                    }
                }
            }
        }


        private fun <T> createData(t: T): Observable<T> {
            return Observable.create {
                try {
                    it.onNext(t)
                    it.onComplete()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }
}