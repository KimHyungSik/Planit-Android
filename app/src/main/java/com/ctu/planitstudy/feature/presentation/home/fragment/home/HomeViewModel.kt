package com.ctu.planitstudy.feature.presentation.home.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.domain.use_case.dday.GetDdayListUseCase
import com.ctu.planitstudy.feature.domain.use_case.study.StudyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ddayListUseCase: GetDdayListUseCase,
    private val studyUseCase: StudyUseCase
) : BaseViewModel() {

    val TAG = "HomeViewModel - 로그"

    private val _homeState = MutableLiveData<HomeState>(HomeState())
    val homeState: LiveData<HomeState> = _homeState

    fun initSet() {
        viewModelScope.launch {
            ddayListUseCase().onEach {
                when (it) {
                    is Resource.Success -> {
                        _homeState.value = homeState.value!!.copy(
                            dDayList = it.data
                        )
                        loadingDismiss()
                    }
                    is Resource.Loading -> {
                        loadingShow()
                    }
                    is Resource.Error -> {
                        loadingErrorDismiss()
                    }
                }
            }.launchIn(this)
        }
    }

    fun changeStudyDate(date: String) {
        studyUseCase.getStudyListUseCase(date).onEach {
            when (it) {
                is Resource.Success -> {
                    _homeState.value = homeState.value!!.copy(
                        studyListDto = it.data!!
                    )
                    loadingDismiss()
                }
                is Resource.Loading -> {
                    loadingShow()
                }
                is Resource.Error -> {
                    loadingErrorDismiss()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun changeStudyIsDone(studyId: String, isDone: Boolean) {
        studyUseCase.editStudyIsDoneUseCase(studyId, isDone).onEach {
            when (it) {
                is Resource.Success -> {
                    loadingDismiss()
                }
                is Resource.Error -> {
                    loadingErrorDismiss()
                }
                is Resource.Loading -> {
                    loadingShow()
                }
            }
        }.launchIn(viewModelScope)
    }
}
