package com.ctu.planitstudy.feature.presentation.dday

import android.util.Log
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.domain.model.Dday
import com.ctu.planitstudy.feature.domain.use_case.dday.DdayUseCase
import com.ctu.planitstudy.feature.presentation.dday.dialog.EmptyTitleCheckDialog
import com.ctu.planitstudy.feature.presentation.dday.dialog.RepresentativeCheckDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class DdayViewModel @Inject constructor(
    private val ddayUseCase: DdayUseCase
) : ViewModel() {
    val TAG = "DdayViewmodel - 로그"

    private val _dDayState = MutableLiveData<DdayState>(DdayState())
    val dDayState: LiveData<DdayState> = _dDayState

    private val _dDayNetworkState = MutableLiveData<DdayNetworkState>()
    val dDayNetworkState: LiveData<DdayNetworkState> = _dDayNetworkState

    private val _dDayDialogState = MutableLiveData<DdayDialogState>(DdayDialogState())
    val dDayDialogState: LiveData<DdayDialogState> = _dDayDialogState


    private val dateFormatDdayDto = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private val dateFormatText = SimpleDateFormat("yyyy년MM월dd일", Locale.KOREA)


    var ddayDto: DdayDto? = null

    fun dDayDtoSet(ddayDto: DdayDto) {
        this.ddayDto = ddayDto
    }

    fun dDayUpdate(ddayDto: DdayDto, date: String) {
        _dDayState.value = DdayState(ddayDto.title, date, ddayDto.color, ddayDto.isRepresentative)
    }

    fun dDayUpdate(ddayState: DdayState) {
        _dDayState.value = ddayState
    }

    fun onSwitchChanged(buttonView: CompoundButton, isChecked: Boolean) {
        _dDayState.value = dDayState.value!!.copy(representative = isChecked)
    }

    fun onSwitchChanged(isChecked: Boolean) {
        _dDayState.value = dDayState.value!!.copy(representative = isChecked)
    }

    fun dDayDeleteDialog() {
        _dDayDialogState.value = dDayDialogState.value!!.copy(deleteDialog = true)
    }

    fun dDayDelete() {
        ddayUseCase.deleteDdayUseCase(ddayDto!!.id).onEach {
            when (it) {
                is Resource.Success -> {
                    _dDayNetworkState.value = DdayNetworkState(deleteDay = true)
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                    _dDayNetworkState.value = DdayNetworkState(loading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun dDayConfirmed() {
        if (dDayState!!.value!!.title.isBlank()) {
            _dDayDialogState.value = dDayDialogState.value!!.copy(emptyTitleDialog = true)
        } else {
            return
        }
        // 디 데이 수정
        if (ddayDto != null) {
            ddayUseCase.modifiedDdayUseCase(getDday(), ddayDto!!.id).onEach {
                when (it) {
                    is Resource.Success -> {
                        _dDayNetworkState.value = DdayNetworkState(modifiedDay = true)
                    }
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {
                        _dDayNetworkState.value = DdayNetworkState(loading = true)
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
                    }
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {
                        _dDayNetworkState.value = DdayNetworkState(loading = true)
                    }
                }
            }.launchIn(viewModelScope)

        }
    }

    fun getDday(): Dday =
        Dday(
            dDayState.value!!.title,
            dateFormatDdayDto.format(
                dateFormatText.parse(
                    dDayState!!.value!!.date.slice(
                        IntRange(
                            0,
                            11
                        )
                    )
                )
            ),
            dDayState!!.value!!.color,
            dDayState!!.value!!.representative
        )
}