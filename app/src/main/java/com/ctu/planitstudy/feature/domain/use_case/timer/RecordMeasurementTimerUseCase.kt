package com.ctu.planitstudy.feature.domain.use_case.timer

import com.ctu.planitstudy.feature.domain.repository.TimerRepository
import javax.inject.Inject

class RecordMeasurementTimerUseCase @Inject constructor(
    val timerRepository: TimerRepository
) {
    operator fun invoke(){

    }
}