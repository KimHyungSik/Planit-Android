package com.ctu.planitstudy.feature.domain.use_case.user

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userApiRepository: UserRepository
) {

    val TAG = "GetUserUseCase - 로그"

    operator fun invoke(): Flow<Resource<UserInformationDto>> = flow {
        try {
            emit(Resource.Loading<UserInformationDto>(null))
            val user = userApiRepository.getUser()
            emit(Resource.Success(user))
        } catch (e: NullPointerException) {
            emit(Resource.Error<UserInformationDto>(message = "NullPointerException " + e.message))
        } catch (e: Exception) {
            emit(Resource.Error<UserInformationDto>(message = "Exception " + e.message))
            if (e is HttpException) {
                emit(
                    Resource.Error<UserInformationDto>(
                        message = JSONObject(
                            e.response()!!.errorBody()!!.string()
                        ).toString()
                    )
                )
            }
        } catch (e: Throwable) {
            emit(Resource.Error<UserInformationDto>(message = "Throwable " + e.message))
        }
    }
}
