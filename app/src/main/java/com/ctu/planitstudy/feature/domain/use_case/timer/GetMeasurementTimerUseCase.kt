package com.ctu.planitstudy.feature.domain.use_case.timer

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.domain.repository.TimerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetMeasurementTimerUseCase @Inject constructor(
    val timerRepository: TimerRepository
) {

    val TAG = "GetMeasurementTimer - 로그"

    operator fun invoke(studyId: String): Flow<Resource<TimerMeasurementDto>> = flow {
        try {
            emit(Resource.Loading<TimerMeasurementDto>(null))
            val timerMeasurementDto = timerRepository.getMeasurementTime(studyId)
            emit(Resource.Success(timerMeasurementDto))
        } catch (e: NullPointerException) {
            emit(Resource.Error<TimerMeasurementDto>(message = "NullPointerException" + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<TimerMeasurementDto>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<TimerMeasurementDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<TimerMeasurementDto>(message = "Throwable" + e.message))
        }
    }
}
