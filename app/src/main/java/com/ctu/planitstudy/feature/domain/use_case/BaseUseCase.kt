package com.ctu.planitstudy.feature.domain.use_case

import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.presentation.util.ActivityLifeCycleObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException

abstract class BaseUseCase<T : Any?> {
    val TAGV = "BaseUseCase - 로그"
    fun useCase(arg: suspend () -> T): Flow<Resource<T>> = flow {
        try {
            Log.d(TAGV, "useCase: ")
            emit(Resource.Loading<T>(null))
            val result = arg()
            val finalResult = result?.let {
                modified(result)
            } ?: result
            emit(Resource.Success(finalResult))
        } catch (e: NullPointerException) {
            emit(Resource.Error<T>(message = "NullPointerException " + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<T>(message = "Exception " + e.message))
            if (e is HttpException) {
                if (e.code() == 401)
                    emit(Resource.Error<T>(data = null, message = "토큰 만료"))
                if (e.code() == 412){
                    Log.d(TAGV, "message = \"앱 버전 이상\"")
                    emit(Resource.Error<T>(data = null, message = "앱 버전 이상"))
                }
                emit(
                    Resource.Error<T>(
                        message = "HttpException " + JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<T>(message = "Throwable " + e.message))
        }
    }

    open fun modified(result: T): T {
        return result
    }
}
