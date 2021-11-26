package com.ctu.planitstudy.di

import com.ctu.planitstudy.core.util.CoreData
import com.ctu.planitstudy.feature.data.remote.TimerApi
import com.ctu.planitstudy.feature.data.repository.TimerRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.TimerRepository
import com.ctu.planitstudy.feature.domain.use_case.timer.GetMeasurementTimerUseCase
import com.ctu.planitstudy.feature.domain.use_case.timer.RecordMeasurementTimerUseCase
import com.ctu.planitstudy.feature.domain.use_case.timer.TimerUseCase
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
object TimerModule {

    @Provides
    @Singleton
    fun provideTimerApi(): TimerApi =
        Retrofit.Builder()
            .baseUrl(CoreData.BASE_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TimerApi::class.java)

    @Provides
    @Singleton
    fun providerTimerRepository(timerApi: TimerApi): TimerRepository =
        TimerRepositoryImp(timerApi)

    @Provides
    @Singleton
    fun providerGetMeasurementTimerUseCase(timerRepository: TimerRepository) : GetMeasurementTimerUseCase =
        GetMeasurementTimerUseCase(timerRepository)

    @Provides
    @Singleton
    fun providerRecordMeasurementTimerUseCase(timerRepository: TimerRepository) : RecordMeasurementTimerUseCase =
        RecordMeasurementTimerUseCase(timerRepository)

    @Provides
    @Singleton
    fun providerTimerUseCase(
        getMeasurementTimerUseCase: GetMeasurementTimerUseCase,
        recordMeasurementTimerUseCase: RecordMeasurementTimerUseCase
    ) : TimerUseCase = TimerUseCase(
        getMeasurementTimerUseCase,
        recordMeasurementTimerUseCase
    )

}
