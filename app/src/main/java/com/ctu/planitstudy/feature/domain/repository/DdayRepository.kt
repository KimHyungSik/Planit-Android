package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.model.Dday
import retrofit2.Response

interface DdayRepository {
    suspend fun getDdayList(): Response<DdayListDto>
    suspend fun addDday(dday: Dday): Response<DdayDto>
    suspend fun modifiedDday(dday: Dday, dDayId: Int): Response<DdayDto>
    suspend fun deleteDday(dDayId: Int): Response<Unit>
}
