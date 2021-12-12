package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.RewardApi
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import javax.inject.Inject

class RewardRepositoryImp @Inject constructor(
    private val rewardApi: RewardApi
) : RewardRepository {
    override suspend fun getRewardPlanet(): PlanetListDto = rewardApi.getRewardPlanet()
}
