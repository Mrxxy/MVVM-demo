package com.goldmantis.mvvm.app.ui

import com.goldmantis.mvvm.core.api.BaseResponse
import com.goldmantis.mvvm.app.api.RetrofitClient
import com.goldmantis.mvvm.app.api.UserRoleBean
import com.google.gson.JsonObject

class LoginRepository {

    suspend fun login(account: String, password: String): BaseResponse<List<UserRoleBean>> {
        return RetrofitClient.apiService.erpLogin(JsonObject().apply {
            addProperty("phone", account)
            addProperty("password", password)
        })
    }

}