package com.goldmantis.mvvm.app.api

import com.goldmantis.mvvm.core.api.BaseResponse
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @Headers("sysName:YHQ", "sysToken:558240477e1b11ea8fb0005056bb5176")
    @POST("/pay/unionpay/{payType}")
    suspend fun orderInfo(
        @Path("payType") payType: String,
        @Body body: JsonObject
    ): BaseResponse<String>

    companion object {
        const val BASE_URL: String = "http://appservice-rest.jtljia.net/"
    }

    @POST("/services/v1/user/erpLogin")
    suspend fun erpLogin(@Body req: JsonObject): BaseResponse<List<UserRoleBean>>

}