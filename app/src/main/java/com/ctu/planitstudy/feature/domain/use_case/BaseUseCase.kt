package com.ctu.planitstudy.feature.domain.use_case

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import kotlin.reflect.KSuspendFunction0

abstract class BaseUseCase<T: Any> {
    fun  useCase(arg: suspend () -> T): Flow<Resource<T>> = flow{
        try {
            emit(Resource.Loading<T>(null))
            val result = arg()
            val finalResult = modified(result)
            emit(Resource.Success(finalResult))
        } catch (e: NullPointerException) {
            emit(Resource.Error<T>(message = "NullPointerException" + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<T>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<T>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<T>(message = "Throwable" + e.message))
        }
    }

    open fun modified(result: T): T{
        return result
    }
}