package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.model.Dday
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import retrofit2.Response

class FakeDdayRepository : DdayRepository {

    val ddays = mutableListOf<Dday>()
    private val ddayListDto = mutableListOf<DdayDto>()
    private val fakeDdayDto = DdayDto(1, "", false, "", "", "", 1)

    override suspend fun getDdayList(): Response<DdayListDto> {
        val listDdayDto = mutableListOf<DdayDto>()
        ddays.forEachIndexed { index, dday ->
            listDdayDto.add(
                DdayDto(
                    id = index,
                    title = dday.title,
                    isRepresentative = dday.isRepresentative,
                    endAt = dday.endAt,
                    createdAt = dday.endAt,
                    icon = dday.icon,
                    dDay = 0
                )
            )
        }
        return Response.success(DdayListDto(listDdayDto))
    }

    override suspend fun addDday(dday: Dday): Response<DdayDto> {
        ddays.add(dday)
        return Response.success(fakeDdayDto)
    }

    override suspend fun modifiedDday(dday: Dday, dDayId: Int): Response<DdayDto> {
        ddays.set(0, dday)
        return Response.success(fakeDdayDto)
    }

    override suspend fun deleteDday(dDayId: Int): Response<Unit> {
        ddays.removeAt(dDayId)
        return Response.success(Unit)
    }
}
