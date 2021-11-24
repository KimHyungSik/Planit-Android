package com.ctu.planitstudy.feature.domain.model.study

import com.google.gson.annotations.SerializedName

data class AddStudy(
    @SerializedName("title")
    val title: String,
    @SerializedName("startAt")
    val startAt: String,
)
