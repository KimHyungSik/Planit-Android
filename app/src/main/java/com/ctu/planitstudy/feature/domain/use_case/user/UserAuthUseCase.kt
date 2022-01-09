package com.ctu.planitstudy.feature.domain.use_case.user

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.google.gson.JsonElement
import io.reactivex.Flowable
import javax.inject.Inject

class UserAuthUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun userLogin(loginUser: LoginUser): LoginDto =
        userRepository.userLogin(loginUser)

    suspend fun userSignUp(signUpUser: SignUpUser): SignUpUserDto =
        userRepository.userSignUp(signUpUser)

    suspend fun userSignUp(signUpUser: SignUpUserReceiver): SignUpUserDto =
        userRepository.userSignUp(signUpUser)
}
