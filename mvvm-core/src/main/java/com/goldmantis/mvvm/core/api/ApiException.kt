package com.goldmantis.mvvm.core.api

class ApiException(var status: String, override val message: String?) : Exception()

class HttpException(var message: String?)