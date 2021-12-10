package com.ctu.planitstudy.feature.presentation.measurement

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.core.util.longToTimeKorString
import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import com.ctu.planitstudy.feature.domain.use_case.timer.TimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MeasurementViewModel @Inject constructor(
    val timerUseCase: TimerUseCase
) : BaseViewModel() {

    val TAG = "MeasurementViewModel - 로그"

    private val _measurementState = MutableLiveData(MeasurementState())
    val measurementState: LiveData<MeasurementState> = _measurementState

    fun getExistingMeasurementTime() {
        timerUseCase.getMeasurementTimerUseCase(measurementState.value!!.studyDto!!.studyId.toString())
            .onEach {
                when (it) {
                    is Resource.Success -> {
                        setViewDataWithTimerData(it.data!!)
                        _loading.value = false
                    }
                    is Resource.Loading -> {
                        _loading.value = true
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "getExistingMeasurementTime Error: ${it.message}")
                        _loading.value = false
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun updateMeasurementState(measurementState: MeasurementState) {
        _measurementState.value = measurementState
    }

    private fun setViewDataWithTimerData(timerMeasurementDto: TimerMeasurementDto) {
        with(timerMeasurementDto) {
            with(measurementState.value!!) {
                _measurementState.value = measurementState.value!!.copy(
                    totalMeasurementTime = (recordedTime.toLong() + measurementTime.toLong()).longToTimeKorString(),
                    extraTime = recordedTime,
                    totalTime = measurementTime.toInt() + recordedTime,
                    totalBrakeTime = totalBrakeTime,
                    totalStar = (measurementState.value!!.measurementTime / 500).toInt(),
                    totalTicket = (measurementState.value!!.measurementTime / 3600).toInt(),
                    isDone = isDone
                )
            }
        }
    }

    fun recordMeasurementTimer(isDone: Boolean) {

        val recordMeasurementTimer =
            // 새로 측정한 경우
            if (measurementState.value!!.extraTime == 0) RecordMeasurementTimer(
                isDone = isDone,
                star = measurementState.value!!.totalStar,
                bonusTicket = measurementState.value!!.totalTicket,
                rest = measurementState.value!!.totalBrakeTime,
                recordedTime = measurementState.value!!.totalTime
            )
            // 이어서 측정한 경우
            else
                RecordMeasurementTimer(
                    isDone = isDone,
                    star = measurementState.value!!.totalStar,
                    bonusTicket = measurementState.value!!.totalTicket,
                    rest = measurementState.value!!.totalBrakeTime,
                    recordedTime = measurementState.value!!.measurementTime.toInt()
                )

        Log.d(TAG, "recordMeasurementTimer: $recordMeasurementTimer")

        timerUseCase.recordMeasurementTimerUseCase(
            measurementState.value!!.studyDto!!.studyId.toString(),
            recordMeasurementTimer
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    _measurementState.value = measurementState.value!!.copy(
                        onExit = true
                    )
                    loadingDismiss()
                }
                is Resource.Error -> {
                    Log.d(TAG, "recordMeasurementTimer: Error ${it.message}")
                    loadingDismiss()
                }
                is Resource.Loading -> {
                    loadingShow()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMeasurementTime(): String =
        measurementState.value!!.measurementTime.longToTimeKorString()
}
