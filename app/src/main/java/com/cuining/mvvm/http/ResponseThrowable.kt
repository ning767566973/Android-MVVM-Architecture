package com.cuining.mvvm.http


/**
 * @author: cuining
 * @date: 2020/4/24
 */
class ResponseThrowable : Exception {
    var code: Int?
    var errMsg: String?

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        code = error.getKey()
        errMsg = error.getValue()
    }

    constructor(code: Int, msg: String, e: Throwable? = null) : super(e) {
        this.code = code
        this.errMsg = msg
    }

    constructor(base: BaseResponse<*>, e: Throwable? = null) : super(e) {
        this.code = base.errorCode
        this.errMsg = base.errorMsg
    }
}

