package com.ctu.planitstudy.feature.presentation.timer

data class TimerState(
    val time: Long = 0,
    val timeString: String = "00:00:00",
    val timerCycle: TimerCycle = TimerCycle.TimeReady
)

enum class TimerCycle {
    TimeReady,
    TimeFlow,
    TimePause,
    TimeStop,
    TimeBrake,
    TimerExited
}
