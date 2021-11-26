package com.ctu.planitstudy.feature.presentation.measurement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctu.planitstudy.feature.domain.use_case.timer.TimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeasurementViewModel @Inject constructor(
    private val timerUseCase: TimerUseCase
) : ViewModel() {

    private val _measurementState = MutableLiveData(MeasurementState())
    val measurementState : LiveData<MeasurementState> = _measurementState

    fun getExistingMeasurementTime(){

    }

}