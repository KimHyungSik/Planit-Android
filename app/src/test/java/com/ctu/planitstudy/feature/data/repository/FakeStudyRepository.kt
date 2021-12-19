package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import retrofit2.Response

class FakeStudyRepository : StudyRepository {
    override suspend fun addStudy(study: Study) {
        TODO("Not yet implemented")
    }

    override suspend fun addStudy(repeatedStudy: RepeatedStudy) {
        TODO("Not yet implemented")
    }

    override suspend fun validateTitle(title: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getStudyList(date: String): StudyListDto {
        TODO("Not yet implemented")
    }

    override suspend fun getStudyTimeLine(date: String): StudyTimeLineDto {
        TODO("Not yet implemented")
    }

    override suspend fun editStudy(studyGroupId: String, studyScheduleId: String, study: Study) {
        TODO("Not yet implemented")
    }

    override suspend fun editStudy(
        studyGroupId: String,
        studyScheduleId: String,
        repeatedStudy: RepeatedStudy
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun editStudyIsDone(studyId: String, isDone: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStudy(studyGroupId: String): Response<Unit> {
        TODO("Not yet implemented")
    }


}