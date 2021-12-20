package com.ctu.planitstudy.feature.presentation.dday

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.domain.model.Dday
import com.ctu.planitstudy.feature.domain.use_case.dday.DdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DdayViewModel @Inject constructor(
    private val ddayUseCase: DdayUseCase
) : BaseViewModel() {
    val TAG = "DdayViewmodel - 로그"

    private val _dDayState = MutableLiveData<DdayState>(DdayState())
    val dDayState: LiveData<DdayState> = _dDayState

    private val _dDayNetworkState = MutableLiveData<DdayNetworkState>(DdayNetworkState())
    val dDayNetworkState: LiveData<DdayNetworkState> = _dDayNetworkState

    private val _dDayDialogState = MutableLiveData<DdayDialogState>(DdayDialogState())
    val dDayDialogState: LiveData<DdayDialogState> = _dDayDialogState

    private var ddayDto: DdayDto? = null

    fun dDayDtoSet(ddayDto: DdayDto) {
        this.ddayDto = ddayDto
    }

    fun dDayUpdate(ddayDto: DdayDto, date: String) {
        _dDayState.value = DdayState(ddayDto.title, date, ddayDto.icon, ddayDto.isRepresentative)
    }

    fun dDayUpdate(ddayState: DdayState) {
        _dDayState.value = ddayState
    }

    fun dDayDialogUpdate(ddayState: DdayDialogState) {
        _dDayDialogState.value = ddayState
    }

    fun onSwitchChanged(buttonView: CompoundButton, isChecked: Boolean) {
        _dDayState.value = dDayState.value!!.copy(representative = isChecked)
    }

    fun onSwitchChanged(isChecked: Boolean) {
        _dDayState.value = dDayState.value!!.copy(representative = isChecked)
    }

    fun dDayDeleteDialog() {
        _dDayDialogState.value = DdayDialogState(deleteDialog = true)
    }

    fun dDayDelete() {
        ddayUseCase.deleteDdayUseCase(ddayDto!!.id).onEach {
            when (it) {
                is Resource.Success -> {
                    _dDayNetworkState.value = DdayNetworkState(deleteDay = true)
                    loadingDismiss()
                }
                is Resource.Error -> {
                    loadingErrorDismiss()
                }
                is Resource.Loading -> {
                    _dDayNetworkState.value = DdayNetworkState(loading = true)
                    loadingShow()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun dDayConfirmed() {
        if (dDayState!!.value!!.title.isBlank()) {
            _dDayDialogState.value = dDayDialogState.value!!.copy(emptyTitleDialog = true)
            return
        }
        // 디 데이 수정
        if (ddayDto != null) {
            ddayUseCase.modifiedDdayUseCase(getDday(), ddayDto!!.id).onEach {
                when (it) {
                    is Resource.Success -> {
                        _dDayNetworkState.value = DdayNetworkState(modifiedDay = true)
                        loadingDismiss()
                    }
                    is Resource.Error -> {
                        loadingErrorDismiss()
                    }
                    is Resource.Loading -> {
                        _dDayNetworkState.value = DdayNetworkState(loading = true)
                        loadingShow()
                    }
                }
            }.launchIn(viewModelScope)
        }
        // 디 데이 추가
        else {
            ddayUseCase.addDayUseCase(getDday()).onEach {
                when (it) {
                    is Resource.Success -> {
                        _dDayNetworkState.value = DdayNetworkState(addDday = true)
                        loadingDismiss()
                    }
                    is Resource.Error -> {
                        loadingDismiss()
                    }
                    is Resource.Loading -> {
                        _dDayNetworkState.value = DdayNetworkState(loading = true)
                        loadingShow()
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getDday(): Dday =
        Dday(
            dDayState.value!!.title,
            DateConvter.textDateToDtoDate(dDayState.value!!.date),
            dDayState.value!!.icon,
            dDayState.value!!.representative
        )
}
