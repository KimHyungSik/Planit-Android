package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import com.google.gson.JsonElement
import io.reactivex.Flowable

interface UserRepository {
    fun userLogin(loginUser: LoginUser): Flowable<JsonElement>
    fun userSignUp(signUpUser: SignUpUser): Flowable<JsonElement>
    fun userSignUp(signUpUser: SignUpUserReceiver): Flowable<JsonElement>
    suspend fun userValidateNickName(nickname: String)
    suspend fun userValidateNickName(nickname: String, previousNickname: String)
    suspend fun getUser(): UserInformationDto
    suspend fun editUser(editUser: EditUser): MessageDto
}
