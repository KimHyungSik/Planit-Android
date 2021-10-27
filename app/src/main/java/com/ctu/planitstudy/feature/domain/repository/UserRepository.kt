package com.ctu.planitstudy.feature.domain.repository

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.domain.model.LoginUser
import io.reactivex.Flowable

interface UserRepository {
    fun userLogin(loginUser: LoginUser): Flowable<LoginDto>
}