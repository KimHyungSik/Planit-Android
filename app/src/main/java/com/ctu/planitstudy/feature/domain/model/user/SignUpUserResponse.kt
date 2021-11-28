package com.ctu.planitstudy.feature.domain.model.user

data class SignUpUserResponse(
    val responseCode: Int? = null,
    val accessToken: String = "",
    val refreshToken: String = "",
)
