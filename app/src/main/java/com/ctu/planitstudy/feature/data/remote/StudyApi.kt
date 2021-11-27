package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.*

interface StudyApi {
    @POST("/v1/study")
    suspend fun addStudy(@Body study: Study)
    @POST("/v1/study")
    suspend fun addStudy(@Body repeatedStudy: RepeatedStudy)
    @GET("/v1/study/validate-title")
    suspend fun validateTitle(@Query("title") title: String)
    @GET("/v1/study/list/{date}")
    suspend fun getStudyList(@Path("date") date: String): StudyListDto
    @PUT("/v1/study/{studyGroupId}/{studyScheduleId}")
    suspend fun editStudy(@Path("studyGroupId") studyGroupId: String, @Path("studyScheduleId") studyScheduleId: String, @Body study: Study)
    @PUT("/v1/study/{studyGroupId}/{studyScheduleId}")
    suspend fun editStudy(@Path("studyGroupId") studyGroupId: String, @Path("studyScheduleId") studyScheduleId: String, @Body repeatedStudy: RepeatedStudy)
    @DELETE("/v1/study/{studyGroupId}")
    suspend fun deleteStudy(@Path("studyGroupId") studyGroupId: String): Response<Unit>
}
