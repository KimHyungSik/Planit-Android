package com.ctu.planitstudy.feature.presentation.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctu.planitstudy.core.util.longToTimeString
import java.util.*

class TimerViewModel :
    ViewModel() {

    val TAG = "TimerViewModel - 로그"

    private val _timerState = MutableLiveData<TimerState>(TimerState())
    val timerState: LiveData<TimerState> = _timerState

    var timer = Timer()

    fun startTimer() {
        _timerState.value = timerState.value!!.copy(
            timerCycle = TimerCycle.TimeFlow
        )
        timer = kotlin.concurrent.timer(period = 1000) {
            _timerState.postValue(
                timerState.value!!.copy(
                    time = _timerState.value!!.time + 1,
                    timeString = (_timerState.value!!.time + 1).longToTimeString(),
                    star = ((_timerState.value!!.time + 1) / (300 * (timerState.value!!.star + 1))).toInt(),
                    ticket = ((_timerState.value!!.time + 1) / (3600 * (timerState.value!!.ticket + 1))).toInt(),
                )
            )
        }
    }

    fun stopTimer() {
        timer.cancel()
    }

    fun changeTimerCycle(timerCycle: TimerCycle) {
        _timerState.value = timerState.value!!.copy(
            timerCycle = timerCycle
        )
    }
}
