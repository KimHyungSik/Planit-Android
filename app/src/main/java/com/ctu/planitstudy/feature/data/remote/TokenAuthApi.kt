package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.TokenRefreshDto
import com.ctu.planitstudy.feature.domain.model.RefreshToken
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenAuthApi {

    @POST("/v1/auth/update-token")
    suspend fun refreshAccessToken(@Body refreshToken : RefreshToken) : JsonElement
}