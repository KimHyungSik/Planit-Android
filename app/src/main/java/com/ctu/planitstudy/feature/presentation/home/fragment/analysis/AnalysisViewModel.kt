package com.ctu.planitstudy.feature.presentation.home.fragment.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
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

    private val _studyTimeLineDto = MutableLiveData<StudyTimeLineDto>(StudyTimeLineDto(0, emptyList(), 0))
    val studyTimeLineDto: LiveData<StudyTimeLineDto> = _studyTimeLineDto

    fun getStudyList(date: String = LocalDate.now().toString()) {
        studyUseCase.getStudyListUseCase(date).onEach {
            when (it) {
                is Resource.Success -> {
                    _studyDto.value = it.data!!
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

    fun getStudyTimeLIne(date: String = LocalDate.now().toString()) {
        studyUseCase.getStudyTimeLineUseCase(date).onEach {
            when (it) {
                is Resource.Success -> {
                    _studyTimeLineDto.value = it.data!!
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
