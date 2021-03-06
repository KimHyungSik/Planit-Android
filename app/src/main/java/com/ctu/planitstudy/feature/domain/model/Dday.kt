package com.ctu.planitstudy.feature.domain.model

import com.google.gson.annotations.SerializedName

data class Dday(
    @SerializedName("title")
    val title: String,
    @SerializedName("endAt")
    val endAt: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("isRepresentative")
    val isRepresentative: Boolean
)
