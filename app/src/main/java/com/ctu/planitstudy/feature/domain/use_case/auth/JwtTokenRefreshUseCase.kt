package com.ctu.planitstudy.feature.domain.use_case.auth

import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.model.RefreshToken
import com.ctu.planitstudy.feature.domain.repository.AuthRepository
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.util.ActivityLifeCycleObserver.Companion.TAG
import com.google.gson.JsonElement
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class JwtTokenRefreshUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    @DelicateCoroutinesApi
    operator fun invoke() : Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>(null))
            val json = authRepository.refreshAccessToken(RefreshToken(CashStudyApp.prefs.refreshToken!!))
            CashStudyApp.prefs.accessToken = json.asJsonObject["accessToken"].asString!!
            emit(Resource.Success(json.asJsonObject["accessToken"].asString!!))
        }catch (e : Exception){
            if(e is HttpException) {
                Log.d(TAG, "invoke: ${e.code()}")
                if(e.code() == 401)
                emit(Resource.Error<String>(data = null, message = "토큰 만료"))
            }
            emit(Resource.Error<String>(data = null, message = "통신 실패"))
        }
    }
}