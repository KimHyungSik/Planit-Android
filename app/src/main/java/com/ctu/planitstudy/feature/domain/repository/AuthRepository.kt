package com.ctu.planitstudy.feature.domain.repository

import com.google.gson.JsonElement
import retrofit2.http.Body

interface AuthRepository {
    suspend fun refreshAccessToken(refreshToken : String) : JsonElement
}