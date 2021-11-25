package com.ctu.planitstudy.feature.domain.use_case.timer

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import com.ctu.planitstudy.feature.domain.repository.TimerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class RecordMeasurementTimerUseCase @Inject constructor(
    private val timerRepository: TimerRepository
) {
    operator fun invoke(studyId: String, recordMeasurementTimer: RecordMeasurementTimer): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>(false))
            timerRepository.recordMeasurementTime(studyId, recordMeasurementTimer)
            emit(Resource.Success<Boolean>(true))
        } catch (e: Exception) {
            emit(Resource.Error<Boolean>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<Boolean>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<Boolean>(message = "Throwable" + e.message))
        }
    }
}
