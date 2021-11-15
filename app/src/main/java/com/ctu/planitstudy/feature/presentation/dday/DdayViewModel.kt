package com.ctu.planitstudy.feature.presentation.dday

import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.domain.use_case.dday.DdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DdayViewModel @Inject constructor(
    private val ddayUseCase: DdayUseCase
) : ViewModel() {
    val TAG = "DdayViewmodel - 로그"
    
    private val _dDayState = MutableLiveData<DdayState>()
    val dDayState : LiveData<DdayState> = _dDayState

    private val _dDayNetworkState = MutableLiveData<DdayNetworkState>()
    val dDayNetworkState : LiveData<DdayNetworkState> = _dDayNetworkState


    var ddayDto: DdayDto? = null

    init {
        _dDayState.value = DdayState()
    }

    fun dDayDtoSet(ddayDto: DdayDto){
        this.ddayDto = ddayDto
    }

    fun dDayUpdate(ddayDto: DdayDto, date: String){
        _dDayState.value = DdayState(ddayDto.title, date, ddayDto.color, ddayDto.isRepresentative)
    }

    fun dDayUpdate(ddayState: DdayState){
        _dDayState.value = ddayState
    }

    fun dDayDelete(){
        Log.d(TAG, "dDayDelete: ${ddayDto!!.id}")
        ddayUseCase.deleteDdayUseCase(ddayDto!!.id).onEach {
            when(it){
                is Resource.Success ->{_dDayNetworkState.value = DdayNetworkState(deleteDay = true)}
                is Resource.Error -> {
                    Log.d(TAG, "dDayDelete: ${it.message}")
                }
                is Resource.Loading ->{_dDayNetworkState.value = DdayNetworkState(loading = true)}
            }
        }.launchIn(viewModelScope)
    }
}