package com.ctu.planitstudy.feature.domain.use_case.reward

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetPlanetPassListUseCase @Inject constructor(
    private val rewardRepository: RewardRepository
) : BaseUseCase<PlanetListDto>() {

    val TAG = "GetPlanetPassListUseCase - 로그"

    operator fun invoke(): Flow<Resource<PlanetListDto>> = useCase { rewardRepository.getRewardPlanet() }
}
