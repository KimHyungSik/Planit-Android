package com.ctu.planitstudy.di

import com.ctu.planitstudy.core.util.CoreData
import com.ctu.planitstudy.core.util.network.NullOnEmptyConverterFactory
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
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object TimerModule {

    @Provides
    fun provideTimerApi(@AuthOkhttpClient okHttpClient: OkHttpClient): TimerApi =
        Retrofit.Builder()
            .baseUrl(CoreData.BASE_SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory().nullOnEmptyConverterFactory)
            .build()
            .create(TimerApi::class.java)

    @Provides
    fun providerTimerRepository(timerApi: TimerApi): TimerRepository =
        TimerRepositoryImp(timerApi)

    @Provides
    fun providerGetMeasurementTimerUseCase(timerRepository: TimerRepository): GetMeasurementTimerUseCase =
        GetMeasurementTimerUseCase(timerRepository)

    @Provides
    fun providerRecordMeasurementTimerUseCase(timerRepository: TimerRepository): RecordMeasurementTimerUseCase =
        RecordMeasurementTimerUseCase(timerRepository)

    @Provides
    fun providerTimerUseCase(
        getMeasurementTimerUseCase: GetMeasurementTimerUseCase,
        recordMeasurementTimerUseCase: RecordMeasurementTimerUseCase
    ): TimerUseCase = TimerUseCase(
        getMeasurementTimerUseCase,
        recordMeasurementTimerUseCase
    )
}
