package com.ctu.planitstudy.feature.presentation.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.core.util.longToTimeString
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyDto
import java.util.*

class TimerViewModel :
    BaseViewModel() {

    val TAG = "TimerViewModel - 로그"

    private val _timerState = MutableLiveData<TimerState>(TimerState())
    val timerState: LiveData<TimerState> = _timerState

    private val _timerCycle = MutableLiveData<TimerCycle>(TimerCycle.TimeReady)
    val timerCycle: LiveData<TimerCycle> = _timerCycle

    var study: StudyDto? = null

    var timer = Timer()
    var timerThreadState: Boolean = false

    fun startTimer() {
        timerThreadState = true
        timer = kotlin.concurrent.timer(period = 1000) {
            _timerState.postValue(
                timerState.value!!.copy(
                    time = _timerState.value!!.time + 1,
                    timeString = (study!!.recordedTime + (_timerState.value!!.time + 1)).longToTimeString(),
                    star = ((study!!.recordedTime + (_timerState.value!!.time + 1)) / 300).toInt(),
                    ticket = ((study!!.recordedTime + (_timerState.value!!.time + 1)) / 3600).toInt(),
                )
            )
        }
    }

    fun setStudyDto(studyDto: StudyDto) {
        study = studyDto
        _timerState.postValue(
            timerState.value!!.copy(
                breakTime = studyDto.rest
            )
        )
    }

    fun stopTimer() {
        timerThreadState = false
        timer.cancel()
    }

    fun updateTimerState(timerState: TimerState) {
        _timerState.value = timerState
    }

    fun changeTimerCycle(timerCycle: TimerCycle) {
        if (this.timerCycle.value!! != timerCycle)
            _timerCycle.value = timerCycle
    }
}
