package com.ctu.planitstudy.feature.data.repository

import com.ctu.planitstudy.feature.data.remote.UserAuthApi
import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.LoginUser
import com.ctu.planitstudy.feature.domain.model.SignUpUser
import com.ctu.planitstudy.feature.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val authApi : UserAuthApi
) : UserRepository {
    override fun userLogin(loginUser : LoginUser): Flowable<LoginDto> = authApi.userLogin(loginUser)
    override fun userSignUp(signUpUser: SignUpUser): Flowable<SignUpUserDto> = authApi.userSignUp(signUpUser)
}