package com.ctu.planitstudy.feature.presentation.study

import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.Weekday

data class StudyState (
    val singleAt : String = "",
    val startAt : String = "",
    val endAt : String = "",
    val week: ArrayList<Weekday> = ArrayList()
    )