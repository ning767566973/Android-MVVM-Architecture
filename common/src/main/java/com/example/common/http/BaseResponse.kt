package com.example.common.http

/**
 * @author: cuining
 * @date: 2020/4/17
 */
data class BaseResponse<T>(
    var errorMsg: String?,
    var errorCode: Int?,
    var data: T
)