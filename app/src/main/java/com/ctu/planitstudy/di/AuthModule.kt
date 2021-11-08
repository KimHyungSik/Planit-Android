package com.ctu.planitstudy.di

import com.ctu.planitstudy.core.util.CoreData
import com.ctu.planitstudy.feature.data.remote.TokenAuthApi
import com.ctu.planitstudy.feature.data.repository.AuthRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.AuthRepository
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

object AuthModule {

    @Provides
    @Singleton
    fun provideTokenAuthApi() : TokenAuthApi =
        Retrofit.Builder()
            .baseUrl(CoreData.BASE_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenAuthApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRepositoryImp(tokenAuthApi: TokenAuthApi) : AuthRepository =
        AuthRepositoryImp(tokenAuthApi)
}