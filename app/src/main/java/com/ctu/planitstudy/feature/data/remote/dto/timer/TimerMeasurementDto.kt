package com.ctu.planitstudy.feature.data.remote.dto.timer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimerMeasurementDto(
    @SerializedName("bonusTicket")
    val bonusTicket: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isDone")
    val isDone: Boolean,
    @SerializedName("recordedTime")
    val recordedTime: Int,
    @SerializedName("rest")
    val rest: Int,
    @SerializedName("star")
    val star: Int,
    @SerializedName("startAt")
    val startAt: String
) : Parcelable
