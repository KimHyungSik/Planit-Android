package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import retrofit2.Response

interface TimerRepository {
    suspend fun recordMeasurementTime(studyId: String, recordMeasurementTimer: RecordMeasurementTimer): Response<MessageDto>
    suspend fun getMeasurementTime(studyId: String): Response<TimerMeasurementDto>
}
