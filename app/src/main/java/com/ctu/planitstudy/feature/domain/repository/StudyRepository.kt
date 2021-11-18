package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.domain.model.study.AddRepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.AddStudy
import retrofit2.http.Query

interface StudyRepository {
    suspend fun addStudy(addStudy: AddStudy)
    suspend fun addStudy(addRepeatedStudy: AddRepeatedStudy)
    suspend fun validateTitle(@Query("title") title : String)
}