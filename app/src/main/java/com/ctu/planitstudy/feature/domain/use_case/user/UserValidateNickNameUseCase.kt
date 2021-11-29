package com.ctu.planitstudy.feature.domain.use_case.user

import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserValidateNickNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    val TAG = "ValidateName - 로그"

    operator fun invoke(nickname: String, previousNickname: String? = null): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading(data = false))
            if (previousNickname == null)
                userRepository.userValidateNickName(nickname)
            else
                userRepository.userValidateNickName(nickname, previousNickname)
            emit(Resource.Success(true))
        } catch (e: HttpException) {
            Log.d(TAG, "invoke: $e")
            if (e.code() == 409)
                emit(Resource.Success(false))
            else
                emit(Resource.Error<Boolean>(data = false, message = e.message()))
        }
    }
}
