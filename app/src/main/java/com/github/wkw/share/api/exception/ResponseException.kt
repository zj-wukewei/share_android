package com.github.wkw.share.api.exception

import com.github.wkw.share.api.ShareResponse

class ResponseException : Exception {
    constructor(shareResponse: ShareResponse<*>) : super(shareResponse.msg)
}