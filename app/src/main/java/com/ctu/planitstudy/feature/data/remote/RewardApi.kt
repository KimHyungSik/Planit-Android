package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RewardApi {

    // 플래닛 패스 리스트 조회
    @GET("/v1/reward/planet")
    suspend fun getRewardPlanet(): PlanetListDto

    // 리워드 관련 정보 조회
    @GET("/v1/reward")
    suspend fun getReward(): RewardDto

    // 별 -> 포인트 전환 api
    @PUT("/v1/reward/convert-star-to-point")
    suspend fun convertStarToPoint(): RewardDto

    // 플래닛 패스로 별 획득 api
    @POST("/v1/reward/star-with-ad/{planetId}")
    suspend fun convertPlanetPassToPoint(@Path("planetId") planetId: String): RewardDto
}
