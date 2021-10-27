package com.ctu.planitstudy.feature.domain.use_case.user

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.domain.model.LoginUser
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(loginUser: LoginUser): Flowable<LoginDto> =
        userRepository.userLogin(loginUser)
}