package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import retrofit2.Response

interface StudyRepository {
    suspend fun addStudy(study: Study) : Response<Unit>
    suspend fun addStudy(repeatedStudy: RepeatedStudy) : Response<Unit>
    suspend fun validateTitle(title: String) : Response<Unit>
    suspend fun getStudyList(date: String): Response<StudyListDto>
    suspend fun getStudyTimeLine(date: String): Response<StudyTimeLineDto>
    suspend fun editStudy(studyGroupId: String, studyScheduleId: String, study: Study): Response<Unit>
    suspend fun editStudy(studyGroupId: String, studyScheduleId: String, repeatedStudy: RepeatedStudy): Response<Unit>
    suspend fun editStudyIsDone(studyId: String, isDone: Boolean): Response<Unit>
    suspend fun deleteStudy(studyGroupId: String): Response<Unit>
}
