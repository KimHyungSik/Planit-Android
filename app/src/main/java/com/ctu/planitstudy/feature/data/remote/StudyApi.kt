package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.domain.model.study.AddRepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.AddStudy
import com.google.gson.JsonElement
import retrofit2.http.*

interface StudyApi {
    @POST("/v1/study")
    suspend fun addStudy(@Body addStudy: AddStudy)
    @POST("/v1/study")
    suspend fun addStudy(@Body addRepeatedStudy: AddRepeatedStudy)
    @GET("/v1/study/validate-title")
    suspend fun validateTitle(@Query("title") title : String)
    @GET("/v1/study/list/{date}")
    suspend fun getStudyList(@Path("date") date : String) : JsonElement
}