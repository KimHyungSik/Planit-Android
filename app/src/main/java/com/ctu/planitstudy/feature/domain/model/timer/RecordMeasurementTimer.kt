package com.ctu.planitstudy.feature.domain.model.timer

import com.google.gson.annotations.SerializedName

data class RecordMeasurementTimer(
    @SerializedName("bonusTicket")
    val bonusTicket: Int,
    @SerializedName("isDone")
    val isDone: Boolean,
    @SerializedName("recordedTime")
    val recordedTime: Int,
    @SerializedName("rest")
    val rest: Int,
    @SerializedName("star")
    val star: Int
)
