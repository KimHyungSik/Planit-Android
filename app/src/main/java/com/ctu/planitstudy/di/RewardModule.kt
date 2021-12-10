package com.ctu.planitstudy.di

import com.ctu.planitstudy.feature.data.remote.RewardApi
import com.ctu.planitstudy.feature.data.repository.RewardRepositoryImp
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import com.ctu.planitstudy.feature.domain.use_case.reward.GetPlanetPassListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RewardModule {

    @Provides
    fun providerRewardApi(retrofit: Retrofit) : RewardApi =
        retrofit
            .create(RewardApi::class.java)


    @Provides
    fun providerRewardRepository(rewardApi: RewardApi) : RewardRepository =
        RewardRepositoryImp(rewardApi)

    @Provides
    fun providerGetPlanetPassListUseCase(rewardRepository: RewardRepository) : GetPlanetPassListUseCase =
        GetPlanetPassListUseCase(rewardRepository)
}