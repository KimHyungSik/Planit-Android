package com.ctu.planitstudy.di

import com.ctu.planitstudy.core.base.network.AuthInterceptor
import com.ctu.planitstudy.core.util.CoreData
import com.ctu.planitstudy.core.util.CoreData.BASE_SERVER_URL
import com.ctu.planitstudy.feature.data.remote.DdayApi
import com.ctu.planitstudy.feature.data.repository.DdayRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import com.ctu.planitstudy.feature.domain.use_case.dday.GetDdayListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DdayModule {

    @Provides
    @Singleton
    fun providerDdayApi(okHttpClient: OkHttpClient): DdayApi =
        Retrofit.Builder()
            .baseUrl(BASE_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DdayApi::class.java)

    @Provides
    @Singleton
    fun providerDdayRepository(ddayApi: DdayApi): DdayRepository =
        DdayRepositoryImp(ddayApi)


    @Provides
    @Singleton
    fun providerGetDdayListUseCase(ddayRepository: DdayRepository) : GetDdayListUseCase =
        GetDdayListUseCase(ddayRepository)
}