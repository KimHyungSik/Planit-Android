package com.plcoding.cleanarchitecturenoteapp.di

import com.ctu.planitstudy.core.util.CoreData
import com.ctu.planitstudy.core.util.network.AuthInterceptor
import com.ctu.planitstudy.core.util.network.NullOnEmptyConverterFactory
import com.ctu.planitstudy.core.util.network.TokenAuthenticator
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerTokenAuthenticator(jwtTokenRefreshUseCase: JwtTokenRefreshUseCase): TokenAuthenticator =
        TokenAuthenticator(jwtTokenRefreshUseCase)

    @Provides
    @Singleton
    fun providerOkhttpClient(jwtTokenRefreshUseCase: JwtTokenRefreshUseCase, tokenAuthenticator: TokenAuthenticator): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(jwtTokenRefreshUseCase))
            // 로그 확인용 인터럽트
//            .addInterceptor(LogginInterceptor.loggingInterceptor)
//            .addNetworkInterceptor(LogginInterceptor.interceptor)
            .authenticator(tokenAuthenticator)
            .build()

    @Provides
    @Singleton
    fun providerRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(CoreData.BASE_SERVER_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(NullOnEmptyConverterFactory().nullOnEmptyConverterFactory)
        .build()
}
