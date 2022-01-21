package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import retrofit2.Response

interface UserRepository {
    suspend fun userLogin(loginUser: LoginUser): LoginDto
    suspend fun userSignUp(signUpUser: SignUpUser): SignUpUserDto
    suspend fun userSignUp(signUpUser: SignUpUserReceiver): SignUpUserDto
    suspend fun userValidateNickName(nickname: String)
    suspend fun userValidateNickName(nickname: String, previousNickname: String)
    suspend fun getUser(): UserInformationDto
    suspend fun editUser(editUser: EditUser): MessageDto
    suspend fun deleteUser(): Response<Unit>
}
