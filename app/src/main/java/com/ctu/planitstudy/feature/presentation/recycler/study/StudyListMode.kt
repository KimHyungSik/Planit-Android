package com.ctu.planitstudy.feature.presentation.recycler.study

sealed class StudyListMode {
    object HomeStudyListMode : StudyListMode()
    object PlannerStudyListMode : StudyListMode()
}
