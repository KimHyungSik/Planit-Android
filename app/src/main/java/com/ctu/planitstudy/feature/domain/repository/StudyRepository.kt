package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.domain.model.study.AddRepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.AddStudy
import com.google.gson.JsonElement

interface StudyRepository {
    suspend fun addStudy(addStudy: AddStudy)
    suspend fun addStudy(addRepeatedStudy: AddRepeatedStudy)
    suspend fun validateTitle(title: String)
    suspend fun getStudyList(date: String): JsonElement
}
