package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto

interface DdayRepository {
    suspend fun getDdayList() : DdayListDto
}