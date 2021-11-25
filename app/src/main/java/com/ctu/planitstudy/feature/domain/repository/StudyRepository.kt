package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.google.gson.JsonElement
import retrofit2.Response

interface StudyRepository {
    suspend fun addStudy(study: Study)
    suspend fun addStudy(repeatedStudy: RepeatedStudy)
    suspend fun validateTitle(title: String)
    suspend fun getStudyList(date: String): JsonElement
    suspend fun editStudy(studyGroupId: String, studyScheduleId: String, study: Study)
    suspend fun editStudy(studyGroupId: String, studyScheduleId: String, repeatedStudy: RepeatedStudy)
    suspend fun deleteStudy(studyGroupId: String): Response<Unit>
}
