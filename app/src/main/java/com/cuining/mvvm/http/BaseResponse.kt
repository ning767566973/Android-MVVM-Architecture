package com.cuining.mvvm.http

/**
 * @author: cuining
 * @date: 2020/4/17
 */
data class BaseResponse<T>(
    var errorMsg: String?,
    var errorCode: Int?,
    var data: T
)