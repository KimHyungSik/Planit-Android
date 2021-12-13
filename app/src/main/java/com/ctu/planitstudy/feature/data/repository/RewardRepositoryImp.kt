package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.RewardApi
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import javax.inject.Inject

class RewardRepositoryImp @Inject constructor(
    private val rewardApi: RewardApi
) : RewardRepository {
    override suspend fun getRewardPlanet(): PlanetListDto = rewardApi.getRewardPlanet()
    override suspend fun getReward(): RewardDto = rewardApi.getReward()
    override suspend fun convertStarToPoint(): RewardDto = rewardApi.convertStarToPoint()
    override suspend fun convertPlanetPassToPoint(planetId: String): RewardDto = rewardApi.convertPlanetPassToPoint(planetId)
}
