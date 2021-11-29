package com.ctu.planitstudy.di

import com.ctu.planitstudy.feature.data.remote.DdayApi
import com.ctu.planitstudy.feature.data.repository.DdayRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.ctu.planitstudy.feature.domain.use_case.dday.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DdayModule {

    @Provides
    fun providerDdayApi(retrofit: Retrofit): DdayApi =
        retrofit
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
