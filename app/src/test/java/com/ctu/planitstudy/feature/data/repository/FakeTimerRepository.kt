package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import com.ctu.planitstudy.feature.domain.repository.TimerRepository

class FakeTimerRepository : TimerRepository {
    override suspend fun recordMeasurementTime(
        studyId: String,
        recordMeasurementTimer: RecordMeasurementTimer
    ): MessageDto {
        TODO("Not yet implemented")
    }

    override suspend fun getMeasurementTime(studyId: String): TimerMeasurementDto {
        TODO("Not yet implemented")
    }
}