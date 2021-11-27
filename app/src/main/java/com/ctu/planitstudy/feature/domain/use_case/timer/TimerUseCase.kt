package com.ctu.planitstudy.feature.domain.use_case.timer

data class TimerUseCase(
    val getMeasurementTimerUseCase: GetMeasurementTimerUseCase,
    val recordMeasurementTimerUseCase: RecordMeasurementTimerUseCase
)
