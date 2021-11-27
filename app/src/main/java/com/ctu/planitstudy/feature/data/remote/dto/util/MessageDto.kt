package com.ctu.planitstudy.feature.data.remote.dto.util

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("message")
    val message: String
)
