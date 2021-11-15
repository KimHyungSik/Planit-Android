package com.ctu.planitstudy.feature.presentation.dday

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
    private val _dDayDto = MutableLiveData<DdayDto>()
    val dDayDto : LiveData<DdayDto> = _dDayDto


    fun dDayUpdate(ddayDto: DdayDto){
        _dDayDto.value = ddayDto
    }
}