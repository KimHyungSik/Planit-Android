package com.ctu.planitstudy.core.util.network

import android.util.Log
import com.ctu.planitstudy.core.util.isJsonArray
import com.ctu.planitstudy.core.util.isJsonObject
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LogginInterceptor {
    private var retrofitClient: Retrofit? = null
    val TAG = "LogginInterceptor"

    val client = OkHttpClient
        .Builder()

    // 로그용 인터럽트
    val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {

            when {
                message.isJsonObject() -> {
                    Log.d(TAG, "loggingInterceptor" + JSONObject(message).toString(4))
                }
                message.isJsonArray() -> {
                    Log.d(TAG, "loggingInterceptor" + JSONObject(message).toString(4))
                }
                else -> {
                    try {
                        Log.d(TAG, JSONObject(message).toString(4))
                    } catch (e: Exception) {
                        Log.d(TAG, message)
                    }
                }
            }
        }
    })

    // 세팅용 인터럽트
    val interceptor: Interceptor = (
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {

                val originalRequest = chain.request()

                val response = chain.proceed(originalRequest)

                Log.d(TAG, "intercept: request : ${response.request}")
                Log.d(TAG, "intercept: message : ${response.message}")
                Log.d(TAG, "intercept: code : ${response.code}")
                Log.d(TAG, "intercept: protocol : ${response.protocol}")
                response.body.let {
                    Log.d(TAG, "intercept: ${JSONObject(response.peekBody(2048).string())}\n\n")
                }

                return response
            }
        }
        )

    fun getOkHtpCLient(): OkHttpClient {
        client.addInterceptor(loggingInterceptor)
        client.addNetworkInterceptor(interceptor)
        return this.client.build()
    }

    fun getClient(baseUrl: String): Retrofit? {

        // 인터럽트 추가
        client.addInterceptor(loggingInterceptor)
        client.addNetworkInterceptor(interceptor)

        val gson = GsonBuilder().setLenient().create()

        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build()
        }

        return retrofitClient
    }
}
