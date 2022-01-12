package com.ctu.planitstudy.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkhttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NonAuthOkhttpClient
