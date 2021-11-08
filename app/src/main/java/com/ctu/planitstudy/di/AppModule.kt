package com.plcoding.cleanarchitecturenoteapp.di

import com.ctu.planitstudy.core.util.CoreData.BASE_SERVER_URL
import com.ctu.planitstudy.feature.data.remote.TokenAuthApi
import com.ctu.planitstudy.feature.data.remote.UserAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



}