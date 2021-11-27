package com.ctu.planitstudy.di

import com.ctu.planitstudy.core.util.CoreData.BASE_SERVER_URL
import com.ctu.planitstudy.core.util.network.NullOnEmptyConverterFactory
import com.ctu.planitstudy.feature.data.remote.DdayApi
import com.ctu.planitstudy.feature.data.repository.DdayRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.ctu.planitstudy.feature.domain.use_case.dday.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DdayModule {

    @Provides
    fun providerDdayApi(okHttpClient: OkHttpClient): DdayApi =
        Retrofit.Builder()
            .baseUrl(BASE_SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory().nullOnEmptyConverterFactory)
            .build()
            .create(DdayApi::class.java)

    @Provides
    fun providerDdayRepository(ddayApi: DdayApi): DdayRepository =
        DdayRepositoryImp(ddayApi)

    @Provides
    fun providerGetDdayListUseCase(ddayRepository: DdayRepository): GetDdayListUseCase =
        GetDdayListUseCase(ddayRepository)

    @Provides
    fun providerAddDdayUseCase(ddayRepository: DdayRepository): AddDdayUseCase =
        AddDdayUseCase(ddayRepository)

    @Provides
    fun providerModifiedDdayUseCase(ddayRepository: DdayRepository): ModifiedDdayUseCase =
        ModifiedDdayUseCase(ddayRepository)

    @Provides
    fun providerDeleteDdayUseCase(ddayRepository: DdayRepository): DeleteDdayUseCase =
        DeleteDdayUseCase(ddayRepository)

    @Provides
    fun providerDdayUseCase(
        addDdayUseCase: AddDdayUseCase,
        deleteDdayUseCase: DeleteDdayUseCase,
        getDdayListUseCase: GetDdayListUseCase,
        modifiedDdayUseCase: ModifiedDdayUseCase
    ): DdayUseCase =
        DdayUseCase(addDdayUseCase, deleteDdayUseCase, getDdayListUseCase, modifiedDdayUseCase)
}
