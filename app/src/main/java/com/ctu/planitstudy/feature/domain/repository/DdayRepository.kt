package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.model.Dday
import com.google.gson.JsonElement
import retrofit2.Response

interface DdayRepository {
    suspend fun getDdayList(): DdayListDto
    suspend fun addDday(dday: Dday): DdayDto
    suspend fun modifiedDday(dday: Dday, dDayId: Int): DdayDto
    suspend fun deleteDday(dDayId: Int): Response<Unit>
}
