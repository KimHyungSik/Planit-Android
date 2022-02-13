package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.domain.model.RefreshToken
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TokenAuthApi {

    @Headers("Content-Type: application/json")
    @POST("/v1/auth/update-token")
    suspend fun refreshAccessToken(@Body refreshToken: RefreshToken): Response<JsonElement>
}
