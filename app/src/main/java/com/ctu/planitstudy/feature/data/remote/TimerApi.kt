package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import com.google.gson.JsonElement
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface TimerApi {

    @PUT("/v1/study/record/{studyId}")
    suspend fun recordMeasurementTime(@Path("studyId") studyId : String, @Body recordMeasurementTimer: RecordMeasurementTimer) : JsonElement

    @GET("/v1/study/record/{studyId}")
    suspend fun getMeasurementTime(@Path("studyId") studyId : String) : JsonElement
}