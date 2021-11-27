package com.ctu.planitstudy.feature.domain.use_case.timer

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
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

    val TAG = "RecordMeasurementTimer - 로그"

    operator fun invoke(studyId: String, recordMeasurementTimer: RecordMeasurementTimer): Flow<Resource<MessageDto>> = flow {
        try {
            emit(Resource.Loading<MessageDto>(null))
            val message = timerRepository.recordMeasurementTime(studyId, recordMeasurementTimer)
            emit(Resource.Success<MessageDto>(message))
        } catch (e: Exception) {
            emit(Resource.Error<MessageDto>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<MessageDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<MessageDto>(message = "Throwable" + e.message))
        }
    }
}
