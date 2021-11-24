package com.ctu.planitstudy.feature.domain.use_case.dday

import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class DeleteDdayUseCase @Inject constructor(
    private val ddayRepository: DdayRepository
) {

    val TAG = "DeleteUseCase - 로그"
    operator fun invoke(ddayId: Int): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>(null))
            val responseBody = ddayRepository.deleteDday(ddayId)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error<Boolean>(message = e.message!!))
            if (e is HttpException)
                Log.d(TAG, "invoke: ${e.message()}")
        }
    }
}
