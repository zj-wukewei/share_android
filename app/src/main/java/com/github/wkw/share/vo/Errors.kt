package com.github.wkw.share.vo

/**
 * @author GoGo on 2019-06-01.
 */
sealed class Errors : Throwable {
    constructor() : super()

    constructor(message: String?): super(message)

    object NetworkError : Errors()
    class EmptyInputError( msg: String?) : Errors(msg)
}