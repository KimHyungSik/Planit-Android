package com.ctu.planitstudy.feature.domain.use_case.user

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserUseCase @Inject
constructor(
    val userRepository: UserRepository
) : BaseUseCase<Unit>() {
    operator fun invoke(): Flow<Resource<Unit>> = useCase { userRepository.deleteUser() }
}
