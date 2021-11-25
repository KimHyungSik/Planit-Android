package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.StudyApi
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.google.gson.JsonElement
import javax.inject.Inject

class StudyRepositoryImp @Inject constructor(
    private val studyApi: StudyApi
) : StudyRepository {
    override suspend fun addStudy(study: Study) = studyApi.addStudy(study)

    override suspend fun addStudy(repeatedStudy: RepeatedStudy) = studyApi.addStudy(repeatedStudy)

    override suspend fun validateTitle(title: String) = studyApi.validateTitle(title)

    override suspend fun getStudyList(date: String): JsonElement = studyApi.getStudyList(date)

    override suspend fun editStudy(studyGroupId : String, study: Study) = studyApi.editStudy(studyGroupId, study)

    override suspend fun editStudy(studyGroupId : String, repeatedStudy: RepeatedStudy)  = studyApi.editStudy(studyGroupId, repeatedStudy)
}
