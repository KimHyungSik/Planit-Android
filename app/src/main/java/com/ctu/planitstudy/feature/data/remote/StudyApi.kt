package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.study.StudyListDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface StudyApi {

    // 공부 추가
    @POST("/v1/study")
    suspend fun addStudy(@Body study: Study) : Response<Unit>

    // 공부 추가(반복)
    @POST("/v1/study")
    suspend fun addStudy(@Body repeatedStudy: RepeatedStudy) : Response<Unit>

    // 닉네임 중복 체크
    @GET("/v1/study/validate-title")
    suspend fun validateTitle(@Query("title") title: String) : Response<Unit>

    // 공부 일자별 리스트 가져오기
    @GET("/v1/study/list/{date}")
    suspend fun getStudyList(@Path("date") date: String): Response<StudyListDto>

    // 공부 일자별 타임라인 가져오기
    @GET("/v1/study/daily-report/{date}")
    suspend fun getStudyTimeLine(@Path("date") date: String): Response<StudyTimeLineDto>

    // 공부 수정
    @PUT("/v1/study/{studyGroupId}/{studyScheduleId}")
    suspend fun editStudy(@Path("studyGroupId") studyGroupId: String, @Path("studyScheduleId") studyScheduleId: String, @Body study: Study): Response<Unit>

    // 공부 수정(반복)
    @PUT("/v1/study/{studyGroupId}/{studyScheduleId}")
    suspend fun editStudy(@Path("studyGroupId") studyGroupId: String, @Path("studyScheduleId") studyScheduleId: String, @Body repeatedStudy: RepeatedStudy): Response<Unit>

    // 공부 상태 수정
    @PATCH("/v1/study/modify-status/{studyId}")
    suspend fun editStudyIsDone(@Path("studyId") studyId: String, @Query("isDone") isDone: Boolean): Response<Unit>

    // 공부 삭제
    @DELETE("/v1/study/{studyGroupId}")
    suspend fun deleteStudy(@Path("studyGroupId") studyGroupId: String): Response<Unit>
}
