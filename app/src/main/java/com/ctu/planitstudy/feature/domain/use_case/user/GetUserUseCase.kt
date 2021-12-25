package com.ctu.planitstudy.feature.domain.use_case.user

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userApiRepository: UserRepository
) : BaseUseCase<UserInformationDto>() {

    val TAG = "GetUserUseCase - 로그"

    operator fun invoke(): Flow<Resource<UserInformationDto>> = useCase { userApiRepository.getUser() }
}
