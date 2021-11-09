package com.plcoding.cleanarchitecturenoteapp.di

import android.content.Context
import com.ctu.planitstudy.core.base.network.AuthInterceptor
import com.ctu.planitstudy.core.util.CoreData.BASE_SERVER_URL
import com.ctu.planitstudy.core.util.PreferencesManager
import com.ctu.planitstudy.feature.data.remote.TokenAuthApi
import com.ctu.planitstudy.feature.data.remote.UserAuthApi
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Level
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerOkhttpClient(jwtTokenRefreshUseCase: JwtTokenRefreshUseCase) : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(jwtTokenRefreshUseCase))
            .build()
}