package com.ctu.planitstudy.feature.domain.model

data class SignUpUserResponse(
    val responseCode: Int? = null,
    val accessToken: String = "",
    val refreshToken: String = "",
)
