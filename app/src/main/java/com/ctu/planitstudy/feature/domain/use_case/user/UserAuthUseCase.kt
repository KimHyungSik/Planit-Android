package com.ctu.planitstudy.feature.domain.use_case.user

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
    fun userLogin(loginUser: LoginUser): Flowable<JsonElement> =
        userRepository.userLogin(loginUser)

    fun userSignUp(signUpUser: SignUpUser): Flowable<JsonElement> =
        userRepository.userSignUp(signUpUser)

    fun userSignUp(signUpUser: SignUpUserReceiver): Flowable<JsonElement> =
        userRepository.userSignUp(signUpUser)
}
