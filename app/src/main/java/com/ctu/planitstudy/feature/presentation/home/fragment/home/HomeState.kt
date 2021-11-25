package com.ctu.planitstudy.feature.presentation.home.fragment.home

import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto

data class HomeState(
    val dDayList: DdayListDto? = null,
    val studyListDto: StudyListDto = StudyListDto(emptyList())
)
