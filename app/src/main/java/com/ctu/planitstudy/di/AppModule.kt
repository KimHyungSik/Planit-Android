package com.plcoding.cleanarchitecturenoteapp.di

import com.ctu.planitstudy.core.util.network.AuthInterceptor
import com.ctu.planitstudy.core.util.network.LogginInterceptor
import com.ctu.planitstudy.core.util.network.TokenAuthenticator
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
            .addInterceptor(LogginInterceptor.loggingInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
}
