package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.model.Dday
import com.google.gson.JsonElement
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Response

interface DdayRepository {
    suspend fun getDdayList() : JsonElement
    suspend fun addDday(dday : Dday) : JsonElement
    suspend fun modifiedDday(dday: Dday) : JsonElement
    suspend fun deleteDday(dDayId : Int) : Response<Unit>
}