package com.ctu.planitstudy.feature.presentation.study

import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.Weekday

data class StudyState (
    // yyyy년MM월dd일(요일)
    val singleAt : String = "",
    val startAt : String = "",
    val endAt : String = "",
    val kindDate : KindStudyDate = KindStudyDate.SingleDate,
    val week: ArrayList<Weekday> = ArrayList(),
    val activationWeek: ArrayList<Weekday> = ArrayList(),
    val repeat : Boolean = false,
    var title : String = ""
    )