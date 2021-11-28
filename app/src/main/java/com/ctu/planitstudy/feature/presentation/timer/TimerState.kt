package com.ctu.planitstudy.feature.presentation.timer

data class TimerState(
    val time: Long = 0,
    val timeString: String = "00:00:00",
    val star: Int = 0,
    val ticket: Int = 0,
    val breakTime: Int = 0
)

enum class TimerCycle {
    TimeReady,
    TimeFlow,
    TimePause,
    TimeStop,
    TimeBreak,
    TimerExited
}
