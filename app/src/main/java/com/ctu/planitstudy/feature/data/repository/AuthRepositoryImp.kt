package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.TokenAuthApi
import com.ctu.planitstudy.feature.domain.model.RefreshToken
import com.ctu.planitstudy.feature.domain.repository.AuthRepository
import com.google.gson.JsonElement
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val tokenAuthApi: TokenAuthApi
) : AuthRepository {
    override suspend fun refreshAccessToken(refreshToken: RefreshToken): JsonElement = tokenAuthApi.refreshAccessToken(refreshToken)
}
