package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.UserAuthApi
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
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val authApi : UserAuthApi
) : UserRepository {
    override fun userLogin(loginUser : LoginUser): Flowable<JsonElement> = authApi.userLogin(loginUser)
    override fun userSignUp(signUpUser: SignUpUser): Flowable<JsonElement> = authApi.userSignUp(signUpUser)
    override fun userSignUp(signUpUser: SignUpUserReceiver): Flowable<JsonElement> = authApi.userSignUp(signUpUser)
    override suspend fun userValidateNickName(nickname : String) = authApi.userValidateNickName(nickname)
}