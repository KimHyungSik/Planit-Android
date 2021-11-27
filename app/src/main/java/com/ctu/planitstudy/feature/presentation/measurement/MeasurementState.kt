package com.ctu.planitstudy.feature.presentation.measurement

import com.ctu.planitstudy.feature.data.remote.dto.study.StudyDto

data class MeasurementState(
    val measurementTime: Long = 0,
    val totalMeasurementTime: String = "00시 00분 00초",
    val extraTime: String? = null,
    val totalTime: Int = 0 ,
    val totalBrakeTime: Int = 0,
    val totalStar: Int = 0,
    val totalTicket: Int = 0,
    val studyDto: StudyDto? = null,
    val isDone : Boolean = false,
    val onExit : Boolean = false
)
