package com.ctu.planitstudy.feature.presentation.timer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    timeString = longToString(
                        _timerState.value!!.time + 1,
                    )
                )
            )
        }
    }

    fun stopTimer() {
        timer.cancel()
    }

    fun changeTimerCycle(timerCycle: TimerCycle){
        _timerState.value = timerState.value!!.copy(
            timerCycle = timerCycle
        )
    }

    private fun longToString(time: Long): String {
        var currentTime = time
        val h = time / 3600
        currentTime = time % 3600
        val m = currentTime / 60
        currentTime %= 60
        val s = currentTime
        val string = buildString {
            if (h < 10)
                append(0)
            append(h)
            append(":")
            if (m < 10)
                append(0)
            append(m)
            append(":")
            if (s < 10)
                append(0)
            append(s)
        }
        return string
    }
}
