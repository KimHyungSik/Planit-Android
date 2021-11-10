package com.plcoding.cleanarchitecturenoteapp.di

import com.ctu.planitstudy.core.util.network.AuthInterceptor
import com.ctu.planitstudy.feature.domain.repository.AuthRepository
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
    fun providerOkhttpClient(jwtTokenRefreshUseCase: JwtTokenRefreshUseCase, authRepository: AuthRepository) : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(jwtTokenRefreshUseCase, authRepository))
            .build()
}