package com.ctu.planitstudy.di

import com.ctu.planitstudy.feature.data.remote.RewardApi
import com.ctu.planitstudy.feature.data.repository.RewardRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import com.ctu.planitstudy.feature.domain.use_case.reward.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RewardModule {

    @Provides
    fun providerRewardApi(retrofit: Retrofit): RewardApi =
        retrofit
            .create(RewardApi::class.java)

    @Provides
    fun providerRewardRepository(rewardApi: RewardApi): RewardRepository =
        RewardRepositoryImp(rewardApi)

    @Provides
    fun providerGetPlanetPassListUseCase(rewardRepository: RewardRepository): GetPlanetPassListUseCase =
        GetPlanetPassListUseCase(rewardRepository)

    @Provides
    fun providerGetRewardUseCase(rewardRepository: RewardRepository): GetRewardUseCase =
        GetRewardUseCase(rewardRepository)

    @Provides
    fun providerConvertStarToPointUseCase(rewardRepository: RewardRepository): ConvertStarToPointUseCase =
        ConvertStarToPointUseCase(rewardRepository)

    @Provides
    fun providerConvertPlanetPassToPoint(rewardRepository: RewardRepository): ConvertPlanitPassToPointUseCase =
        ConvertPlanitPassToPointUseCase(rewardRepository)

    @Provides
    fun providerRewardUseCase(
        getPlanetPassListUseCase: GetPlanetPassListUseCase,
        getRewardUseCase: GetRewardUseCase,
        convertStarToPointUseCase: ConvertStarToPointUseCase,
        convertPlanitPassToPointUseCase: ConvertPlanitPassToPointUseCase
    ) : RewardUseCase =
        RewardUseCase(
            getRewardUseCase = getRewardUseCase,
            getPlanetPassListUseCase = getPlanetPassListUseCase,
            convertStarToPointUseCase = convertStarToPointUseCase,
            convertPlanitPassToPointUseCase = convertPlanitPassToPointUseCase
        )
}
