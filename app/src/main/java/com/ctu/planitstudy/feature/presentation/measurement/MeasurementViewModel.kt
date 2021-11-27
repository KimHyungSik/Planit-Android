package com.ctu.planitstudy.feature.presentation.measurement

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
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
) : ViewModel() {

    val TAG = "MeasurementViewModel - 로그"

    private val _measurementState = MutableLiveData(MeasurementState())
    val measurementState: LiveData<MeasurementState> = _measurementState

    fun getExistingMeasurementTime() {

        Log.d(TAG, "getExistingMeasurementTime: ${measurementState.value!!.studyDto!!.studyId}")
        timerUseCase.getMeasurementTimerUseCase(measurementState.value!!.studyDto!!.studyId.toString())
            .onEach {
                when (it) {
                    is Resource.Success -> {
                        Log.d(TAG, "getExistingMeasurementTime: ${it.data}")
                        setViewDataWithTimerData(it.data!!)
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "getExistingMeasurementTime Error: ${it.message}")
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun updateMeasurementState(measurementState: MeasurementState) {
        _measurementState.value = measurementState
    }

    private fun setViewDataWithTimerData(timerMeasurementDto: TimerMeasurementDto) {
        Log.d(TAG, "setViewDataWithTimerData: $timerMeasurementDto")
        Log.d(TAG, "setViewDataWithTimerData: ${measurementState.value!!}")
        with(timerMeasurementDto) {
            with(measurementState.value!!) {
                _measurementState.value = measurementState.value!!.copy(
                    totalMeasurementTime = (recordedTime.toLong() + measurementTime).longToTimeKorString(),
                    extraTime = recordedTime.toLong().longToTimeKorString(),
                    totalTime = measurementTime.toInt() + recordedTime,
                    totalBrakeTime = totalBrakeTime + rest,
                    totalStar = totalStar + star,
                    totalTicket = totalTicket + bonusTicket,
                    isDone = isDone
                )
            }
        }
    }

    fun recordMeasurementTimer(isDone: Boolean) {
        val recordMeasurementTimer = RecordMeasurementTimer(
            isDone = isDone,
            star = measurementState.value!!.totalStar,
            bonusTicket = measurementState.value!!.totalTicket,
            rest = measurementState.value!!.totalBrakeTime,
            recordedTime = measurementState.value!!.totalTime
        )

        Log.d(TAG, "recordMeasurementTimer: $recordMeasurementTimer")
        Log.d(TAG, "recordMeasurementTimer: ${measurementState.value!!.studyDto!!.studyId}")

        timerUseCase.recordMeasurementTimerUseCase(
            measurementState.value!!.studyDto!!.studyId.toString(),
            recordMeasurementTimer
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "recordMeasurementTimer: Success ${it.data}")
                    _measurementState.value = measurementState.value!!.copy(
                        onExit = true
                    )
                }
                is Resource.Error -> {
                    Log.d(TAG, "recordMeasurementTimer: Error")
                }
                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }
}
