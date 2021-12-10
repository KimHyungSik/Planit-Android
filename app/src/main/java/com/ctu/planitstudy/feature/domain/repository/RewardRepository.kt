package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetsDto

interface RewardRepository {
    suspend fun getRewardPlanet() : PlanetsDto
}