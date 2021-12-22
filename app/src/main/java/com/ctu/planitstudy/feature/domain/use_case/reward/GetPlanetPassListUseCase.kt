package com.ctu.planitstudy.feature.domain.use_case.reward

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException

class GetPlanetPassListUseCase @Inject constructor(
    private val rewardRepository: RewardRepository
) {

    val TAG = "GetPlanetPassListUseCase - 로그"

    operator fun invoke(): Flow<Resource<PlanetListDto>> = flow {
        try {
            emit(Resource.Loading<PlanetListDto>(null))
            val rewardPlant = rewardRepository.getRewardPlanet()
            emit(Resource.Success(rewardPlant))
        } catch (e: NullPointerException) {
            emit(Resource.Error<PlanetListDto>(message = "NullPointerException" + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<PlanetListDto>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<PlanetListDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<PlanetListDto>(message = "Throwable" + e.message))
        }
    }
}
