package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.model.Dday
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DdayApi {

    @GET("/v1/dday")
    suspend fun getDdayList(): Response<DdayListDto>

    @POST("/v1/dday")
    suspend fun addDday(@Body dday: Dday): Response<DdayDto>

    @PUT("/v1/dday/{dDayId}")
    suspend fun modifiedDday(@Body dday: Dday, @Path("dDayId") dDayId: Int): Response<DdayDto>

    @DELETE("/v1/dday/{dDayId}")
    suspend fun deleteDday(@Path("dDayId") dDayId: Int): Response<Unit>
}
