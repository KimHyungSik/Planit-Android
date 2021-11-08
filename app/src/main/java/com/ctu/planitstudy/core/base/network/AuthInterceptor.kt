package com.ctu.planitstudy.core.base.network

import com.ctu.planitstudy.feature.presentation.CashStudyApp
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req =
            chain.request().newBuilder().addHeader("Authorization", CashStudyApp.prefs.accessToken ?: "").build()
        return chain.proceed(req)
    }
}