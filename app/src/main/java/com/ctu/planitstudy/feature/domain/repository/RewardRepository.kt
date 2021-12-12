package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto

interface RewardRepository {
    suspend fun getRewardPlanet(): PlanetListDto
}
