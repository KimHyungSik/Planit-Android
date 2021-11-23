package com.ctu.planitstudy.feature.data.remote.dto.study

data class StudyDto(
    val endAt: String,
    val isDone: Boolean,
    val repeatedDays: List<String>?,
    val studyGroupId : Int,
    val studyScheduleId : Int,
    val startAt: String,
    val studyId: Int,
    val title: String
)