package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.domain.model.RefreshToken
import com.google.gson.JsonElement
import retrofit2.Response

interface AuthRepository {
    suspend fun refreshAccessToken(refreshToken: RefreshToken): Response<JsonElement>
}
