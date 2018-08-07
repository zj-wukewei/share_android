package com.github.wkw.share.api

class ShareResponse<T> {
    var code: Int? = null
    var msg: String? = null
    var data: T? = null
}