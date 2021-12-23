package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.google.gson.JsonElement
import io.reactivex.Flowable

class FakeUserRepository : UserRepository {
    override fun userLogin(loginUser: LoginUser): Flowable<JsonElement> {
        TODO("Not yet implemented")
    }

    override fun userSignUp(signUpUser: SignUpUser): Flowable<JsonElement> {
        TODO("Not yet implemented")
    }

    override fun userSignUp(signUpUser: SignUpUserReceiver): Flowable<JsonElement> {
        TODO("Not yet implemented")
    }

    override suspend fun userValidateNickName(nickname: String) {
        TODO("Not yet implemented")
    }

    override suspend fun userValidateNickName(nickname: String, previousNickname: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserInformationDto {
        TODO("Not yet implemented")
    }

    override suspend fun editUser(editUser: EditUser): MessageDto {
        TODO("Not yet implemented")
    }
}