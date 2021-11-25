package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.google.gson.JsonElement
import retrofit2.http.*

interface StudyApi {
    @POST("/v1/study")
    suspend fun addStudy(@Body study: Study)
    @POST("/v1/study")
    suspend fun addStudy(@Body repeatedStudy: RepeatedStudy)
    @GET("/v1/study/validate-title")
    suspend fun validateTitle(@Query("title") title: String)
    @GET("/v1/study/list/{date}")
    suspend fun getStudyList(@Path("date") date: String): JsonElement
    @PUT("/v1/study/{studyGroupId}")
    suspend fun editStudy(@Path("studyGroupId") studyGroupId: String, @Body study: Study)
    @PUT("/v1/study/{studyGroupId}")
    suspend fun editStudy(@Path("studyGroupId") studyGroupId: String, @Body repeatedStudy: RepeatedStudy)
}
