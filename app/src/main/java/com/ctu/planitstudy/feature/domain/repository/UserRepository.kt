package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.LoginUser
import com.ctu.planitstudy.feature.domain.model.SignUpUser
import io.reactivex.Flowable

interface UserRepository {
    fun userLogin(loginUser: LoginUser): Flowable<LoginDto>
    fun userSignUp(signUpUser: SignUpUser): Flowable<SignUpUserDto>
    suspend fun userValidateNickName(nickname : String)
}