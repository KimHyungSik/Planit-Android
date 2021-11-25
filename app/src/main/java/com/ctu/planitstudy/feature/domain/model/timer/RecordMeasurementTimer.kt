package com.ctu.planitstudy.feature.domain.model.timer

data class RecordMeasurementTimer(
    val bonusTicket: Int,
    val isDone: Boolean,
    val recordedTime: Int,
    val rest: Int,
    val star: Int
)
