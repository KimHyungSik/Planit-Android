package com.ctu.planitstudy.feature.data.remote.dto.study

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Report(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("recordedTime")
    val recordedTime: Int,
    @SerializedName("studyId")
    val studyId: Int,
    @SerializedName("title")
    val title: String
) : Parcelable
