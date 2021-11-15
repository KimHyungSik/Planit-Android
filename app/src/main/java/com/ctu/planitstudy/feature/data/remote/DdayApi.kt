package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.domain.model.Dday
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface DdayApi {

    @GET("/v1/dday")
    suspend fun getDdayList(): JsonElement

    @POST("/v1/dday")
    suspend fun addDday(@Body dday : Dday) : JsonElement

    @PUT("/v1/dday")
    suspend fun modifiedDday(@Body dday : Dday) : JsonElement

    @DELETE("/v1/dday/{dDayId}")
    suspend fun deleteDday(@Path("dDayId") dDayId : Int) : Response<Unit>
}