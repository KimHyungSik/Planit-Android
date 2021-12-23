package com.ctu.planitstudy.feature.domain.use_case.user

import app.cash.turbine.test
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.data.repository.FakeUserRepository
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserUseCaseTest {

    private lateinit var userUseCase: UserUseCase
    private lateinit var userRepository: FakeUserRepository

    private var userInformationDto =
        UserInformationDto("2000-00-00", "", "test@test.com", 1, "name", "nickname", "")

    @Before
    fun setUp() {
        userRepository = FakeUserRepository()
        userUseCase = UserUseCase(
            getUserUseCase = GetUserUseCase(userRepository),
            editUserUseCase = EditUserUseCase(userRepository),
            validateNickNameUseCase = UserValidateNickNameUseCase(userRepository)
        )
    }

    @Test
    fun `유저 닉네임 중복 체크 테스트`() = runBlocking{
        userUseCase.validateNickNameUseCase(userInformationDto.nickname).test {
            awaitItem()
            awaitItem()
            assert(userRepository.validateNickName)
            cancelAndConsumeRemainingEvents()
        }
        userUseCase.validateNickNameUseCase(userInformationDto.nickname, "1").test {
            awaitItem()
            awaitItem()
            assert(userRepository.validateNickName)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `유저 조회 테스트`() = runBlocking{
        userUseCase.getUserUseCase().test {
            awaitItem()
            val result = awaitItem().data
            assertEquals(userInformationDto, result)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `유저 수정 테스트`() = runBlocking {
        val editUser = EditUser("Category", "KimHyung", "Nick")
        userUseCase.editUserUseCase(editUser).test {
            awaitItem()
            awaitItem()
            cancelAndConsumeRemainingEvents()
        }
        userUseCase.getUserUseCase().test {
            awaitItem()
            val result = awaitItem().data
            assertEquals(userInformationDto.copy(category = editUser.category, name = editUser.name, nickname = editUser.nickname), result)
            cancelAndConsumeRemainingEvents()
        }
    }
}