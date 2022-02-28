package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import retrofit2.Response

class FakeRewardRepository : RewardRepository {

    val planetDto = mutableListOf<PlanetDto>()
    private var reward = RewardDto(star = 150, point = 0, planetPass = 3)

    override suspend fun getRewardPlanet(): Response<PlanetListDto> =
        Response.success(PlanetListDto(planetDto))

    override suspend fun getReward(): Response<RewardDto> = Response.success(reward)

    override suspend fun convertStarToPoint(): Response<RewardDto> {
        return Response.success(reward.copy(star = reward.star - 50, point = reward.point + 5))
    }

    override suspend fun convertPlanetPassToPoint(planetId: String): Response<RewardDto> {
        return Response.success(reward.copy(planetPass = reward.planetPass - 1))
    }
}
