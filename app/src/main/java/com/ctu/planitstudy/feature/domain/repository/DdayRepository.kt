package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.domain.model.Dday
import com.google.gson.JsonElement
import retrofit2.Response

interface DdayRepository {
    suspend fun getDdayList(): JsonElement
    suspend fun addDday(dday: Dday): JsonElement
    suspend fun modifiedDday(dday: Dday, dDayId: Int): JsonElement
    suspend fun deleteDday(dDayId: Int): Response<Unit>
}
