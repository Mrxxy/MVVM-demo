package com.goldmantis.mvvm.core.api

class BaseResponse<T>(var data: T, var status: String, var msg: String) {
    fun handleResp(): T {
        if ("1" == status) {
            return data
        } else {
            throw ApiException(status, msg)
        }
    }
}