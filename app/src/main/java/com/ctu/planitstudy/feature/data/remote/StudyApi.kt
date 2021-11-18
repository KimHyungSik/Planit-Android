package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.domain.model.study.AddRepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.AddStudy
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StudyApi {
    @POST("/v1/study")
    suspend fun addStudy(@Body addStudy: AddStudy)
    @POST("/v1/study")
    suspend fun addStudy(@Body addRepeatedStudy: AddRepeatedStudy)
    @GET("/v1/study/validate-title")
    suspend fun validateTitle(@Query("title") title : String)
}