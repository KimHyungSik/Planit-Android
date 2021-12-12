package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import retrofit2.http.GET

interface RewardApi {

    @GET("/v1/reward/planet")
    suspend fun getRewardPlanet(): PlanetListDto
}
