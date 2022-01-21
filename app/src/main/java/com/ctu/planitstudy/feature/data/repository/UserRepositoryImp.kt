package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.UserApi
import com.ctu.planitstudy.feature.data.remote.UserAuthApi
import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val authApi: UserAuthApi,
    private val userApi: UserApi
) : UserRepository {

    val TAG = "UserRepositoryImp - 로그"

    init {
    }

    override suspend fun userLogin(loginUser: LoginUser): LoginDto = authApi.userLogin(loginUser)
    override suspend fun userSignUp(signUpUser: SignUpUser): SignUpUserDto = authApi.userSignUp(signUpUser)
    override suspend fun userSignUp(signUpUser: SignUpUserReceiver): SignUpUserDto = authApi.userSignUp(signUpUser)
    override suspend fun userValidateNickName(nickname: String) = authApi.userValidateNickName(nickname)
    override suspend fun userValidateNickName(nickname: String, previousNickname: String) = authApi.userValidateNickName(nickname, previousNickname)
    override suspend fun getUser(): UserInformationDto = userApi.getUser()
    override suspend fun editUser(editUser: EditUser): MessageDto = userApi.editUser(editUser)
    override suspend fun deleteUser(): Unit = userApi.deleteUser()
}
