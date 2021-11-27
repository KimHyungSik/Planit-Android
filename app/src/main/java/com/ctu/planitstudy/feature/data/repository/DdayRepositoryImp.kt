package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.DdayApi
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.model.Dday
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.google.gson.JsonElement
import retrofit2.Response
import javax.inject.Inject

class DdayRepositoryImp @Inject constructor(
    private val ddayApi: DdayApi
) : DdayRepository {
    override suspend fun getDdayList(): DdayListDto = ddayApi.getDdayList()
    override suspend fun addDday(dday: Dday): DdayDto = ddayApi.addDday(dday)
    override suspend fun modifiedDday(dday: Dday, dDayId: Int): DdayDto = ddayApi.modifiedDday(dday, dDayId)
    override suspend fun deleteDday(dDayId: Int): Response<Unit> = ddayApi.deleteDday(dDayId)
}
