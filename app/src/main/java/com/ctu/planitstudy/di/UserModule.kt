package com.ctu.planitstudy.di

import com.ctu.planitstudy.core.util.CoreData.BASE_SERVER_URL
import com.ctu.planitstudy.feature.data.data_source.user.UserManager
import com.ctu.planitstudy.feature.data.data_source.user.componets.KakaoOauthImp
import com.ctu.planitstudy.feature.data.remote.UserAuthApi
import com.ctu.planitstudy.feature.data.repository.UserRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.ctu.planitstudy.feature.domain.use_case.user.UserAuthUseCase
import com.ctu.planitstudy.feature.domain.use_case.user.UserValidateNickNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideKakaoOauthImp(): KakaoOauthImp =
        KakaoOauthImp()

    @Provides
    @Singleton
    fun provideUserManager(
        kakaoOauthImp: KakaoOauthImp
    ): UserManager =
        UserManager(
            kakaoOauth = kakaoOauthImp
        )

    @Provides
    @Singleton
    fun provideUserAuthApi(): UserAuthApi =
        Retrofit.Builder()
            .baseUrl(BASE_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(UserAuthApi::class.java)

    @Provides
    @Singleton
    fun providerUserRepository(authApi: UserAuthApi): UserRepository =
        UserRepositoryImp(authApi)

    @Provides
    @Singleton
    fun providerUserAuthUseCase(userRepository: UserRepository): UserAuthUseCase =
        UserAuthUseCase(userRepository)

    @Provides
    @Singleton
    fun providerUserValidateNickName(userRepository: UserRepository): UserValidateNickNameUseCase =
        UserValidateNickNameUseCase(userRepository)
}
