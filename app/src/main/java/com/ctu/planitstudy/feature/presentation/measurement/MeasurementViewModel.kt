package com.ctu.planitstudy.feature.presentation.measurement

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.longToTimeKorString
import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
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
        timerUseCase.getMeasurementTimerUseCase(measurementState.value!!.studyDto!!.studyId.toString())
            .onEach {
                when (it) {
                    is Resource.Success -> {
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
        with(timerMeasurementDto) {
            with(measurementState.value!!) {
                _measurementState.value = measurementState.value!!.copy(
                    totalMeasurementTime = if (recordedTime == null) measurementTime.longToTimeKorString() else (recordedTime.toLong() + measurementTime).longToTimeKorString(),
                    extraTime = if (recordedTime != null) measurementTime.longToTimeKorString() else null,
                    totalBrakeTime = totalBrakeTime + rest,
                    totalStar = totalStar + star,
                    totalTicket = totalTicket + bonusTicket
                )
            }
        }
    }
}
