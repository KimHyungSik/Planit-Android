package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.TokenRefreshDto
import com.ctu.planitstudy.feature.domain.model.RefreshToken
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body

interface AuthRepository {
    suspend fun refreshAccessToken(refreshToken : RefreshToken) : JsonElement
}