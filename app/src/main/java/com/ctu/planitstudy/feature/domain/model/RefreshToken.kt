package com.ctu.planitstudy.feature.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class RefreshToken (
    @SerializedName("refreshToken")
    val refreshToken : String
)