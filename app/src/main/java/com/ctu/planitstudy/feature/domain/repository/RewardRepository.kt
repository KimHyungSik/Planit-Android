package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import retrofit2.Response

interface RewardRepository {
    suspend fun getRewardPlanet(): Response<PlanetListDto>
    suspend fun getReward(): Response<RewardDto>
    suspend fun convertStarToPoint(): Response<RewardDto>
    suspend fun convertPlanetPassToPoint(planetId: String): Response<RewardDto>
}
