package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAuthApi {

    @POST("/v1/auth/login/")
    suspend fun userLogin(
        @Body loginUser: LoginUser
    ): Response<LoginDto>

    @POST("/v1/user")
    suspend fun userSignUp(
        @Body signUpUser: SignUpUser
    ): Response<SignUpUserDto>

    @POST("/v1/user")
    suspend fun userSignUp(
        @Body signUpUser: SignUpUserReceiver
    ): Response<SignUpUserDto>

    @GET("/v1/user/validate-nickname")
    suspend fun userValidateNickName(@Query("nickname") nickname: String): Response<Unit>

    @GET("/v1/user/validate-nickname")
    suspend fun userValidateNickName(@Query("nickname") nickname: String, @Query("previousNickname") previousNickname: String): Response<Unit>
}
