package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import com.ctu.planitstudy.feature.domain.repository.TimerRepository
import retrofit2.Response

class FakeTimerRepository : TimerRepository {

    private var timerMeasurementDto = TimerMeasurementDto(0, 1, false, 100, 0, 0, "0000-00-00")

    override suspend fun recordMeasurementTime(
        studyId: String,
        recordMeasurementTimer: RecordMeasurementTimer
    ): Response<MessageDto> {
        with(recordMeasurementTimer) {
            timerMeasurementDto = timerMeasurementDto.copy(bonusTicket = this.bonusTicket, isDone = this.isDone, recordedTime = this.recordedTime, rest = this.rest, star = this.star)
        }
        return Response.success(MessageDto("success"))
    }

    override suspend fun getMeasurementTime(studyId: String): Response<TimerMeasurementDto> = Response.success(timerMeasurementDto)
}
