package com.ctu.planitstudy.feature.domain.use_case.reward

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetsDto
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyTimeLineDto
import com.ctu.planitstudy.feature.domain.repository.RewardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetPlanetPassListUseCase @Inject constructor(
    private val rewardRepository: RewardRepository
) {

    operator fun invoke() : Flow<Resource<PlanetsDto>> = flow {
        try {
            emit(Resource.Loading<PlanetsDto>(null))
            val rewardPlant = rewardRepository.getRewardPlanet()
            emit(Resource.Success(rewardPlant))
        } catch (e: NullPointerException) {
            emit(Resource.Error<PlanetsDto>(message = "NullPointerException" + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<PlanetsDto>(message = "Exception" + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<PlanetsDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<PlanetsDto>(message = "Throwable" + e.message))
        }
    }
}