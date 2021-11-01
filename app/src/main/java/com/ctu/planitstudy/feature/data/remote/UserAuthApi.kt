package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.LoginUser
import com.ctu.planitstudy.feature.domain.model.SignUpUser
import io.reactivex.Flowable
import retrofit2.http.*

interface UserAuthApi {

    @POST("/v1/auth/login")
    fun userLogin(
        @Body loginUser: LoginUser
    ): Flowable<LoginDto>

    @POST("/v1/user")
    fun userSignUp(
        @Body signUpUser: SignUpUser
    ): Flowable<SignUpUserDto>

    @GET("/v1/user/validate-nickname")
    suspend fun userValidateNickName(@Query("nickname") nickname : String )
}