package com.ctu.planitstudy.feature.data.remote.dto.study

import com.google.gson.annotations.SerializedName

data class Report(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("recordedTime")
    val recordedTime: Int,
    @SerializedName("studyId")
    val studyId: Int,
    @SerializedName("title")
    val title: String
)