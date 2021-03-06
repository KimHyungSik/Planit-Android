package com.ctu.planitstudy.di

import com.ctu.planitstudy.core.util.CoreData
import com.ctu.planitstudy.feature.data.remote.TokenAuthApi
import com.ctu.planitstudy.feature.data.repository.AuthRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.AuthRepository
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideTokenAuthApi(): TokenAuthApi =
        Retrofit.Builder()
            .baseUrl(CoreData.BASE_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenAuthApi::class.java)

    @Provides
    fun provideAuthRepositoryImp(tokenAuthApi: TokenAuthApi): AuthRepository =
        AuthRepositoryImp(tokenAuthApi)

    @Provides
    fun providerJwtTokenRefreshUseCase(authRepository: AuthRepository): JwtTokenRefreshUseCase =
        JwtTokenRefreshUseCase(authRepository)
}
