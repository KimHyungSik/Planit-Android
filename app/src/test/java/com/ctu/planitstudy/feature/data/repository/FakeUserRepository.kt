package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import retrofit2.Response

class FakeUserRepository : UserRepository {

    private var userInformationDto = UserInformationDto("2000-00-00", "", "test@test.com", 1, "name", "nickname", "")
    var validateNickName: Boolean = false

    override suspend fun userLogin(loginUser: LoginUser): LoginDto = LoginDto(true, "", "")

    override suspend fun userSignUp(signUpUser: SignUpUser): SignUpUserDto = SignUpUserDto(0, "",  "", "", "", "", "", "", "")
    override suspend fun userSignUp(signUpUser: SignUpUserReceiver): SignUpUserDto = SignUpUserDto(0, "",  "", "", "", "", "", "", "")

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

    override suspend fun deleteUser(): Response<Unit> {
        return Response.success(null)
    }
}
