package com.ctu.planitstudy.feature.domain.use_case.reward

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConvertStarToPointUseCase @Inject constructor(
    private val rewardRepository: RewardRepository
) : BaseUseCase<RewardDto>() {
    operator fun invoke(): Flow<Resource<RewardDto>> = useCase { rewardRepository.convertStarToPoint() }
}
