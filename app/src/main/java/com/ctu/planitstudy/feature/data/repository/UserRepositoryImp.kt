package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.UserApi
import com.ctu.planitstudy.feature.data.remote.UserAuthApi
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.google.gson.JsonElement
import io.reactivex.Flowable
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val authApi: UserAuthApi,
    private val userApi: UserApi
) : UserRepository {

    val TAG = "UserRepositoryImp - 로그"

    init {
    }

    override fun userLogin(loginUser: LoginUser): Flowable<JsonElement> = authApi.userLogin(loginUser)
    override fun userSignUp(signUpUser: SignUpUser): Flowable<JsonElement> = authApi.userSignUp(signUpUser)
    override fun userSignUp(signUpUser: SignUpUserReceiver): Flowable<JsonElement> = authApi.userSignUp(signUpUser)
    override suspend fun userValidateNickName(nickname: String) = userApi.userValidateNickName(nickname)
    override suspend fun getUser(): UserInformationDto = userApi.getUser()
    override suspend fun editUser(editUser: EditUser): MessageDto = userApi.editUser(editUser)
}
