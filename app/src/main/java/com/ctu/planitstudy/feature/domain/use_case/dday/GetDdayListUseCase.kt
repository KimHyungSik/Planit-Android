package com.ctu.planitstudy.feature.domain.use_case.dday

import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.date_util.DateConvter.dtoDateTOLong
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.data.remote.dto.JsonConverter
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
            val jsonElement = ddayRepository.getDdayList()
            val ddayList = JsonConverter.jsonToDdayListDto(jsonElement.asJsonObject)
            val ddaySrot = ddayList.ddays.sortedByDescending { dtoDateTOLong(it.endAt) }
            emit(Resource.Success(DdayListDto(ddaySrot)))
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
}
