package com.ctu.planitstudy.feature.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("result")
    val result: Boolean,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
