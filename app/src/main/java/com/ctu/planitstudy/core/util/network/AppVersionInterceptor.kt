package com.ctu.planitstudy.core.util.network

import android.content.Intent
import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.CoreData.APP_VERSION
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.login.LoginScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppVersionInterceptor : Interceptor {

    val TAG = "AuthInterceptor - 로그"

    override fun intercept(chain: Interceptor.Chain): Response {
        val req =
            chain
                .request()
                .newBuilder()
                .addHeader(
                    "version",
                    APP_VERSION
                )
                .build()

        return chain.proceed(req)
    }

}
