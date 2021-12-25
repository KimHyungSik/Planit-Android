package com.ctu.planitstudy.feature.domain.use_case.timer

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import com.ctu.planitstudy.feature.domain.repository.TimerRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class RecordMeasurementTimerUseCase @Inject constructor(
    private val timerRepository: TimerRepository
) : BaseUseCase<MessageDto>() {

    val TAG = "RecordMeasurementTimer - 로그"

    operator fun invoke(studyId: String, recordMeasurementTimer: RecordMeasurementTimer): Flow<Resource<MessageDto>> = useCase { timerRepository.recordMeasurementTime(studyId, recordMeasurementTimer) }
}
