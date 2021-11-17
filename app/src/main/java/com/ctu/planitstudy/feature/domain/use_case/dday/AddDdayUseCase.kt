package com.ctu.planitstudy.feature.domain.use_case.dday

import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.data.remote.dto.JsonConverter
import com.ctu.planitstudy.feature.domain.model.Dday
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class AddDdayUseCase @Inject constructor(
    private val ddayRepository: DdayRepository
)  {

    val TAG = "AddDdayUseCase - 로그"
    operator fun invoke(dday : Dday) : Flow<Resource<DdayDto>> = flow{
        try {
            emit(Resource.Loading<DdayDto>(null))
            val jsonElement = ddayRepository.addDday(dday)
            val dday= JsonConverter.jsonToDdayDto(jsonElement.asJsonObject)
            emit(Resource.Success(dday))
        }catch (e : Throwable){
            Log.d(TAG, "invoke: ${e.message}}")
            emit(Resource.Error<DdayDto>(
                message = e.localizedMessage
            ))
            if(e is HttpException) {
                emit(
                    Resource.Error<DdayDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        }
    }
}