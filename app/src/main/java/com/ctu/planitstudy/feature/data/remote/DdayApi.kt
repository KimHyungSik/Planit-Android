package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.model.Dday
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.*

interface DdayApi {

    @GET("/v1/dday")
    suspend fun getDdayList(): DdayListDto

    @POST("/v1/dday")
    suspend fun addDday(@Body dday: Dday): DdayDto

    @PUT("/v1/dday/{dDayId}")
    suspend fun modifiedDday(@Body dday: Dday, @Path("dDayId") dDayId: Int): DdayDto

    @DELETE("/v1/dday/{dDayId}")
    suspend fun deleteDday(@Path("dDayId") dDayId: Int): Response<Unit>
}
