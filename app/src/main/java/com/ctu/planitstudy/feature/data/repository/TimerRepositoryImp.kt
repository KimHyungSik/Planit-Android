package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.TimerApi
import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import com.ctu.planitstudy.feature.domain.repository.TimerRepository
import javax.inject.Inject

class TimerRepositoryImp @Inject constructor(
    val timerApi: TimerApi
) : TimerRepository {
    override suspend fun recordMeasurementTime(
        studyId: String,
        recordMeasurementTimer: RecordMeasurementTimer
    ): MessageDto = timerApi.recordMeasurementTime(studyId, recordMeasurementTimer)

    override suspend fun getMeasurementTime(studyId: String): TimerMeasurementDto = timerApi.getMeasurementTime(studyId)
}
