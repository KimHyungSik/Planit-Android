package com.ctu.cashstudy.di

import android.app.Application
import android.content.Context
import com.ctu.cashstudy.feature.data.data_source.user.UserManager
import com.ctu.cashstudy.feature.data.data_source.user.componets.KakaoOauthImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
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