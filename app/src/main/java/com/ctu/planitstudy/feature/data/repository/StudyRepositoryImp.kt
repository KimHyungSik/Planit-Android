package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.StudyApi
import com.ctu.planitstudy.feature.domain.model.study.AddRepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.AddStudy
import com.ctu.planitstudy.feature.domain.repository.StudyRepository
import com.google.gson.JsonElement
import javax.inject.Inject

class StudyRepositoryImp @Inject constructor(
    private val studyApi: StudyApi
): StudyRepository {
    override suspend fun addStudy(addStudy: AddStudy) = studyApi.addStudy(addStudy)

    override suspend fun addStudy(addRepeatedStudy: AddRepeatedStudy) = studyApi.addStudy(addRepeatedStudy)

    override suspend fun validateTitle(title: String) = studyApi.validateTitle(title)

    override suspend fun getStudyList(date: String): JsonElement  = studyApi.getStudyList(date)
}