package com.ctu.planitstudy.feature.presentation.study

import com.ctu.planitstudy.core.util.enums.Weekday

data class StudyState(
    // yyyy년MM월dd일(요일)
    val singleAt: String = "",
    val startAt: String = "",
    val endAt: String = "",
    val kindDate: KindStudyDate = KindStudyDate.SingleDate,
    val week: ArrayList<Weekday> = ArrayList(),
    val activationWeek: ArrayList<Weekday> = ArrayList(),
    val repeat: Boolean = false,
    var title: String = "",
    // null이 아니라면 기존 데이터를 주입 받은 경우
    val studyGroupId: String? = null
)
