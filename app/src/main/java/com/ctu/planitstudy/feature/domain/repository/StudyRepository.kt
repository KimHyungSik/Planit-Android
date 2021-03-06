package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import retrofit2.Response

interface StudyRepository {
    suspend fun addStudy(study: Study)
    suspend fun addStudy(repeatedStudy: RepeatedStudy)
    suspend fun validateTitle(title: String)
    suspend fun getStudyList(date: String): StudyListDto
    suspend fun getStudyTimeLine(date: String): StudyTimeLineDto
    suspend fun editStudy(studyGroupId: String, studyScheduleId: String, study: Study)
    suspend fun editStudy(studyGroupId: String, studyScheduleId: String, repeatedStudy: RepeatedStudy)
    suspend fun editStudyIsDone(studyId: String, isDone: Boolean)
    suspend fun deleteStudy(studyGroupId: String): Response<Unit>
}
