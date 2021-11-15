package com.ctu.planitstudy.core.util.network

import android.content.Intent
import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.repository.AuthRepository
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.login.LoginScreen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AuthInterceptor @Inject constructor(
    private val jwtTokenRefreshUseCase: JwtTokenRefreshUseCase,
) : Interceptor {

    val accessTokenExpiration = JWTAccessTokenExpiration()
    val jwtRefreshTokenExpiration = JWTRefreshTokenExpiration()

    val TAG = "AuthInterceptor - 로그"

    override fun intercept(chain: Interceptor.Chain): Response {

        if(jwtRefreshTokenExpiration()){
            Log.d(TAG, "intercept: refreshToken : ${CashStudyApp.prefs.refreshToken}")
            Intent(CashStudyApp.instance, LoginScreen::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            }.also { CashStudyApp.instance.startActivity(it) }
        }

        // 토큰 만료 검사
        if (accessTokenExpiration() && !jwtRefreshTokenExpiration()) runBlocking {
            getAccessToken()
            delay(800)
        }

        Log.d(TAG, "intercept: 액세스 토큰 검사 : ${CashStudyApp.prefs.accessToken}")

        var req =
            chain
                .request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer " + CashStudyApp.prefs.accessToken ?: ""
                )
                .build()

        return chain.proceed(req)
    }

    private suspend inline fun getAccessToken(): String? =
        GlobalScope.async(Dispatchers.Default) {
            Log.d(TAG, "getAccessToken: ")
            callNewAccessToken()
        }.await()

    private suspend inline fun callNewAccessToken(): String? = suspendCoroutine { continuation ->
        Log.d(TAG, "callNewAccessToken: ")
        jwtTokenRefreshUseCase().onEach {
            Log.d(TAG, "callNewAccessToken: jwtTokenRefreshUseCase")
            when (it) {
                is Resource.Success -> {
                    continuation.resume(it.data)
                }
                is Resource.Error -> {
                    continuation.resume(it.data)
                }
            }
            return@onEach
        }.launchIn(GlobalScope)
        return@suspendCoroutine
    }

}