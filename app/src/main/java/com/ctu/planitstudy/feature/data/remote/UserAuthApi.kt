package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import com.google.gson.JsonElement
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAuthApi {

    @POST("/v1/auth/login")
    suspend fun userLogin(
        @Body loginUser: LoginUser
    ): LoginDto

    @POST("/v1/user")
    suspend fun userSignUp(
        @Body signUpUser: SignUpUser
    ): SignUpUserDto

    @POST("/v1/user")
    suspend fun userSignUp(
        @Body signUpUser: SignUpUserReceiver
    ): SignUpUserDto

    @GET("/v1/user/validate-nickname")
    suspend fun userValidateNickName(@Query("nickname") nickname: String)

    @GET("/v1/user/validate-nickname")
    suspend fun userValidateNickName(@Query("nickname") nickname: String, @Query("previousNickname") previousNickname: String)
}
