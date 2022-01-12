package com.ctu.planitstudy.core.util.network

import com.ctu.planitstudy.core.util.CoreData.APP_VERSION
import okhttp3.Interceptor
import okhttp3.Response

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
