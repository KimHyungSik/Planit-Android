package com.ctu.planitstudy.feature.domain.model.study

import com.google.gson.annotations.SerializedName

data class RepeatedStudy(
    @SerializedName("title")
    val title: String,
    @SerializedName("startAt")
    val startAt: String,
    @SerializedName("endAt")
    val endAt: String,
    @SerializedName("repeatedDays")
    val repeatedDays: ArrayList<String>
)
