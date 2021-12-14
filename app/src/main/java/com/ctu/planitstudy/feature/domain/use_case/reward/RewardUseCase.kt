package com.ctu.planitstudy.feature.domain.use_case.reward

data class RewardUseCase(
    val getRewardUseCase: GetRewardUseCase,
    val getPlanetPassListUseCase: GetPlanetPassListUseCase,
    val convertStarToPointUseCase: ConvertStarToPointUseCase,
    val convertPlanitPassToPointUseCase: ConvertPlanitPassToPointUseCase
)
