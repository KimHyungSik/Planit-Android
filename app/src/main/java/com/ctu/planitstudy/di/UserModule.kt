package com.ctu.planitstudy.di

import com.ctu.planitstudy.feature.data.data_source.user.UserManager
import com.ctu.planitstudy.feature.data.data_source.user.componets.KakaoOauthImp
import com.ctu.planitstudy.feature.data.remote.UserApi
import com.ctu.planitstudy.feature.data.remote.UserAuthApi
import com.ctu.planitstudy.feature.data.repository.UserRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.ctu.planitstudy.feature.domain.use_case.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    fun provideKakaoOauthImp(): KakaoOauthImp =
        KakaoOauthImp()

    @Provides
    fun provideUserManager(
        kakaoOauthImp: KakaoOauthImp
    ): UserManager =
        UserManager(
            kakaoOauth = kakaoOauthImp
        )

    @Provides
    fun provideUserAuthApi(retrofit: Retrofit): UserAuthApi =
        retrofit
            .create(UserAuthApi::class.java)

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit
            .create(UserApi::class.java)

    @Provides
    fun providerUserRepository(authApi: UserAuthApi, userApi: UserApi): UserRepository =
        UserRepositoryImp(authApi, userApi)

    @Provides
    fun providerUserAuthUseCase(userRepository: UserRepository): UserAuthUseCase =
        UserAuthUseCase(userRepository)

    @Provides
    fun providerUserValidateNickName(userRepository: UserRepository): UserValidateNickNameUseCase =
        UserValidateNickNameUseCase(userRepository)

    @Provides
    fun providerGetUserUseCase(userRepository: UserRepository): GetUserUseCase =
        GetUserUseCase(userRepository)

    @Provides
    fun providerEditUserUseCase(userRepository: UserRepository): EditUserUseCase =
        EditUserUseCase(userRepository)

    @Provides
    fun providerUserUseCase(
        getUserUseCase: GetUserUseCase,
        editUserUseCase: EditUserUseCase
    ): UserUseCase =
        UserUseCase(
            getUserUseCase = getUserUseCase,
            editUserUseCase = editUserUseCase
        )
}
