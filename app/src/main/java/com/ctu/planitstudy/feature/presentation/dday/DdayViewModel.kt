package com.ctu.planitstudy.feature.presentation.dday

import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.domain.use_case.dday.DdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DdayViewModel @Inject constructor(
    private val ddayUseCase: DdayUseCase
) : ViewModel() {
    val TAG = "DdayViewmodel - 로그"
    
    private val _dDayState = MutableLiveData<DdayState>()
    val dDayState : LiveData<DdayState> = _dDayState
    lateinit var ddayDto: DdayDto

    init {
        _dDayState.value = DdayState()
    }

    fun dDayDtoSet(ddayDto: DdayDto){
        this.ddayDto =ddayDto
    }

    fun dDayUpdate(ddayDto: DdayDto, date: String){
        _dDayState.value = DdayState(ddayDto.title, date, ddayDto.color, ddayDto.isRepresentative)
    }

    fun dDayUpdate(ddayState: DdayState){
        _dDayState.value = ddayState
    }

}