package com.ctu.planitstudy.feature.presentation.home.fragment.analysis

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.domain.use_case.study.StudyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    val studyUseCase: StudyUseCase
) : BaseViewModel() {

    val TAG = "AnalysisViewModel - 로그"

    private val _studyDto = MutableLiveData<StudyListDto>(StudyListDto("", emptyList()))
    val studyDto: LiveData<StudyListDto> = _studyDto

    fun getStudyList(date: String = LocalDate.now().toString()) {
        Log.d(TAG, "getStudyList: $date")
        studyUseCase.getStudyListUseCase(date).onEach {
            when (it) {
                is Resource.Success -> {
                    _studyDto.value = it.data!!
                    loadingDismiss()
                }
                is Resource.Error -> {
                    Log.d(TAG, "getStudyList: error ${it.message}")
                    loadingDismiss()
                }
                is Resource.Loading -> {
                    loadingShow()
                }
            }
        }.launchIn(viewModelScope)
    }
}
