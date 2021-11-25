package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.TimerApi
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import com.ctu.planitstudy.feature.domain.repository.TimerRepository
import com.google.gson.JsonElement
import javax.inject.Inject

class TimerRepositoryImp @Inject constructor(
    val timerApi: TimerApi
) : TimerRepository {
    override suspend fun recordMeasurementTime(
        studyId: String,
        recordMeasurementTimer: RecordMeasurementTimer
    ): JsonElement = timerApi.recordMeasurementTime(studyId, recordMeasurementTimer)

    override suspend fun getMeasurementTime(studyId: String): JsonElement = timerApi.getMeasurementTime(studyId)
}