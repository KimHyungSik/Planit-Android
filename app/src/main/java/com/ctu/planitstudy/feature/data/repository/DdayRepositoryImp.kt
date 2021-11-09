package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.DdayApi
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.google.gson.JsonElement
import javax.inject.Inject

class DdayRepositoryImp @Inject constructor(
    private val ddayApi: DdayApi
): DdayRepository {
    override suspend fun getDdayList(): DdayListDto = ddayApi.getDdayList()
}