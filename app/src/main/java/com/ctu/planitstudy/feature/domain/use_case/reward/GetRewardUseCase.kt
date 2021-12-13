package com.ctu.planitstudy.feature.domain.use_case.reward

import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetRewardUseCase @Inject constructor(
    private val rewardRepository: RewardRepository
){
    operator fun invoke(): Flow<Resource<RewardDto>> = flow {
        try {
            emit(Resource.Loading<RewardDto>(null))
            val reward = rewardRepository.getReward()
            emit(Resource.Success(reward))
        } catch (e: NullPointerException) {
            emit(Resource.Error<RewardDto>(message = "NullPointerException" + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<RewardDto>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<RewardDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<RewardDto>(message = "Throwable" + e.message))
        }
    }
}