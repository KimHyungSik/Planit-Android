package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.User
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserResponse
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import com.google.gson.JsonElement
import io.reactivex.Flowable

class FakeUserRepository : UserRepository {

    private var userInformationDto = UserInformationDto("2000-00-00", "", "test@test.com",1, "name", "nickname", "")
    var validateNickName : Boolean = false

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
        validateNickName = userInformationDto.nickname == nickname
    }

    override suspend fun userValidateNickName(nickname: String, previousNickname: String) {
        validateNickName = userInformationDto.nickname == nickname
    }

    override suspend fun getUser(): UserInformationDto = userInformationDto

    override suspend fun editUser(editUser: EditUser): MessageDto {
        userInformationDto = userInformationDto.copy(category = editUser.category, name = editUser.name, nickname = editUser.nickname)
        return MessageDto("success")
    }
}