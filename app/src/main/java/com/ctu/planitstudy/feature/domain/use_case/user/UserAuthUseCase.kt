package com.ctu.planitstudy.feature.domain.use_case.user

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.LoginUser
import com.ctu.planitstudy.feature.domain.model.SignUpUser
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

class UserAuthUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun userLogin(loginUser: LoginUser): Flowable<LoginDto> =
        userRepository.userLogin(loginUser)

    fun userSignUp(signUpUser : SignUpUser): Flowable<SignUpUserDto> =
        userRepository.userSignUp(signUpUser)

}