package com.ctu.planitstudy.feature.data.remote.dto.study

import com.google.gson.annotations.SerializedName

data class StudyTimeLineDto(
    @SerializedName("doneStudies")
    val doneStudies: Int,
    @SerializedName("reports")
    val reports: List<Report>,
    @SerializedName("totalStudies")
    val totalStudies: Int
)