package com.ctu.planitstudy.feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TokenRefreshDto(
    @SerializedName("refreshToken")
    val refreshToken: String
)
