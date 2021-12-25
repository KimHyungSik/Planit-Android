package com.ctu.planitstudy.feature.domain.use_case.timer

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.domain.repository.TimerRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMeasurementTimerUseCase @Inject constructor(
    val timerRepository: TimerRepository
) : BaseUseCase<TimerMeasurementDto>() {

    val TAG = "GetMeasurementTimer - 로그"

    operator fun invoke(studyId: String): Flow<Resource<TimerMeasurementDto>> = useCase { timerRepository.getMeasurementTime(studyId) }
}
