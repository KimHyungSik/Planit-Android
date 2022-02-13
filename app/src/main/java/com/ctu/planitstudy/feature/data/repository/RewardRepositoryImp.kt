package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.RewardApi
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import retrofit2.Response
import javax.inject.Inject

class RewardRepositoryImp @Inject constructor(
    private val rewardApi: RewardApi
) : RewardRepository {
    override suspend fun getRewardPlanet(): Response<PlanetListDto> = rewardApi.getRewardPlanet()
    override suspend fun getReward(): Response<RewardDto> = rewardApi.getReward()
    override suspend fun convertStarToPoint(): Response<RewardDto> = rewardApi.convertStarToPoint()
    override suspend fun convertPlanetPassToPoint(planetId: String): Response<RewardDto> = rewardApi.convertPlanetPassToPoint(planetId)
}
