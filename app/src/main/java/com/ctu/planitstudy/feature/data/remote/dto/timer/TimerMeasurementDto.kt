package com.ctu.planitstudy.feature.data.remote.dto.timer

data class TimerMeasurementDto(
    val bonusTicket: Int,
    val id: Int,
    val isDone: Boolean,
    val recordedTime: Int,
    val rest: Int,
    val star: Int,
    val startAt: String
)
