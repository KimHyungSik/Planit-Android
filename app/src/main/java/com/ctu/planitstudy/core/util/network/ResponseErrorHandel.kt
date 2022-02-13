package com.ctu.planitstudy.core.util.network

import okhttp3.Interceptor
import okhttp3.Response

class ResponseErrorHandel : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 412) {
            return response
        }
        return response
    }
}
