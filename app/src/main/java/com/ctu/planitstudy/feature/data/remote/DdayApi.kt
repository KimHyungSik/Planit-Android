package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.google.gson.JsonElement
import retrofit2.http.GET

interface DdayApi {

    @GET("/v1/dday")
    suspend fun getDdayList(): DdayListDto
}