package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.LoginUser
import com.ctu.planitstudy.feature.domain.model.SignUpUser
import com.ctu.planitstudy.feature.domain.model.SignUpUserReceiver
import com.google.gson.JsonElement
import io.reactivex.Flowable
import org.json.JSONObject

interface UserRepository {
    fun userLogin(loginUser: LoginUser): Flowable<JsonElement>
    fun userSignUp(signUpUser: SignUpUser): Flowable<JsonElement>
    fun userSignUp(signUpUser: SignUpUserReceiver): Flowable<JsonElement>
    suspend fun userValidateNickName(nickname : String)
}