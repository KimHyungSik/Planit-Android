package com.ctu.planitstudy.core.base.network

import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val jwtTokenRefreshUseCase: JwtTokenRefreshUseCase
) : Interceptor {

    val accessTokenExpiration = JWTAccessTokenExpiration()

    val TAG = "AuthInterceptor - 로그"

    @DelicateCoroutinesApi
    override fun intercept(chain: Interceptor.Chain): Response {

        // 토큰 만료 검사
        if(accessTokenExpiration()) runBlocking{
            Log.d(TAG, "intercept: 액세스 토큰 검사")
            Log.d(TAG, "intercept: 액세스 토큰 검사 : ${CashStudyApp.prefs.accessToken}")
            jwtTokenRefreshUseCase().onEach {
                when(it){
                    is Resource.Success ->{  Log.d(TAG, "intercept: 액세스 토큰 검사 Success")}
                    is Resource.Loading ->{  Log.d(TAG, "intercept: 액세스 토큰 검사 Loading")}
                    is Resource.Error ->{  Log.d(TAG, "intercept: 액세스 토큰 검사, Error")}
                }
{}            }
            Log.d(TAG, "intercept: 액세스 토큰 검사 : ${CashStudyApp.prefs.accessToken}")
        }

        var req =
            chain.request().newBuilder().addHeader("Authorization", "Bearer " + CashStudyApp.prefs.accessToken ?: "").build()
        Log.d(TAG, "intercept: ${req.headers}")
        return chain.proceed(req)
    }
}