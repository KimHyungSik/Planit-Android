package com.ctu.planitstudy.feature.data.remote.dto.study

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudyDto(
    @SerializedName("endAt")
    val endAt: String,
    @SerializedName("isDone")
    val isDone: Boolean,
    @SerializedName("recordedTime")
    val recordedTime: Int,
    @SerializedName("repeatedDays")
    val repeatedDays: List<String>?,
    @SerializedName("rest")
    val rest: Int,
    @SerializedName("startAt")
    val startAt: String,
    @SerializedName("studyGroupId")
    val studyGroupId: Int,
    @SerializedName("studyId")
    val studyId: Int,
    @SerializedName("studyScheduleId")
    val studyScheduleId: Int,
    @SerializedName("title")
    val title: String
) : Parcelable
