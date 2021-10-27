package com.ctu.planitstudy.di

import com.ctu.planitstudy.feature.data.data_source.user.UserManager
import com.ctu.planitstudy.feature.data.data_source.user.componets.KakaoOauthImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

}