package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto

interface RewardRepository {
    suspend fun getRewardPlanet(): PlanetListDto
    suspend fun getReward(): RewardDto
    suspend fun convertStarToPoint(): RewardDto
    suspend fun convertPlanetPassToPoint(planetId: String): RewardDto

}
