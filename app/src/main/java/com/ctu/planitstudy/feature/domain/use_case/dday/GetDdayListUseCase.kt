package com.ctu.planitstudy.feature.domain.use_case.dday

import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter.dtoDateTOLong
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetDdayListUseCase @Inject constructor(
    private val ddayRepository: DdayRepository
) {

    operator fun invoke(): Flow<Resource<DdayListDto>> = flow {
        try {
            emit(Resource.Loading<DdayListDto>(null))
            val ddayList = ddayRepository.getDdayList()

            for (dday in ddayList.ddays) {
                dday.dDay = DateCalculation().calDateBetween(
                    DateCalculation().getCurrentDateString(0),
                    dday.endAt
                )
            }
            emit(Resource.Success(sortedDdayList(ddayList)))
        } catch (e: Throwable) {
            if (e is HttpException) {
                emit(
                    Resource.Error<DdayListDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        }
    }

    fun sortedDdayList(ddayListDto: DdayListDto): DdayListDto {
        val ddaySort = ddayListDto.ddays.sortedBy { dtoDateTOLong(it.endAt) }
        return DdayListDto(ddaySort)
    }
}
