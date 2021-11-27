package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import com.google.gson.JsonElement

interface TimerRepository {
    suspend fun recordMeasurementTime(studyId: String, recordMeasurementTimer: RecordMeasurementTimer): JsonElement
    suspend fun getMeasurementTime(studyId: String): TimerMeasurementDto
}