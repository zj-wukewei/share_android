package com.github.wkw.share.api

class ShareResponse<T> {
    var code: Int = 0
    var msg: String = ""
    var data: T? = null
}