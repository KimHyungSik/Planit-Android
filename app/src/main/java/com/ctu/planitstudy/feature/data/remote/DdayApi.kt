package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.domain.model.Dday
import com.google.gson.JsonElement
import retrofit2.http.*

interface DdayApi {

    @GET("/v1/dday")
    suspend fun getDdayList(): JsonElement

    @POST("/v1/dday")
    suspend fun addDday(@Body dday : Dday) : JsonElement

    @PUT("/v1/dday")
    suspend fun modifiedDday(@Body dday : Dday) : JsonElement

    @DELETE
    suspend fun deleteDday(@Header("dDayId") dDayId : Int)
}