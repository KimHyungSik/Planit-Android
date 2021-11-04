package com.ctu.planitstudy.feature.domain.use_case.user

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.LoginUser
import com.ctu.planitstudy.feature.domain.model.SignUpUser
import com.ctu.planitstudy.feature.domain.model.SignUpUserReceiver
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import io.reactivex.Flowable
import org.json.JSONObject
import retrofit2.Call
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