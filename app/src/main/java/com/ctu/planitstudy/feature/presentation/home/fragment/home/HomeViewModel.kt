package com.ctu.planitstudy.feature.presentation.home.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.ctu.planitstudy.feature.domain.use_case.dday.GetDdayListUseCase
import com.ctu.planitstudy.feature.domain.use_case.study.GetStudyListUseCase
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ddayListUseCase: GetDdayListUseCase,
    private val getStudyListUseCase: GetStudyListUseCase
) : ViewModel() {

    val TAG = "HomeViewModel - 로그"

    private val _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> = _homeState

    init {
        _homeState.value = HomeState()
        initSet()
    }

    fun initSet() {
        viewModelScope.launch {
            ddayListUseCase().onEach {
                when (it) {
                    is Resource.Success -> {
                        _homeState.value = homeState.value!!.copy(
                            dDayList = it.data
                        )
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "initSet: ${it.message}")
                    }
                }
            }.launchIn(this)
            getStudyListUseCase(DateCalculation().getCurrentDateString(0)).onEach {
                when (it) {
                    is Resource.Success -> {
                        _homeState.value = homeState.value!!.copy(
                            studyListDto = it.data ?: StudyListDto(emptyList())
                        )
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "getStudyList: error ${it.message}")
                    }
                }
            }.launchIn(this)
        }
    }

    fun changeStudyDate(date : String){
        getStudyListUseCase(date).onEach {
            when (it) {
                is Resource.Success -> {
                    _homeState.value = homeState.value!!.copy(
                        studyListDto = it.data ?: StudyListDto(emptyList())
                    )
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                    Log.d(TAG, "getStudyList: error ${it.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

}