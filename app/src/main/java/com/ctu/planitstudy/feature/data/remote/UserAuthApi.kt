package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import com.google.gson.JsonElement
import io.reactivex.Flowable
import retrofit2.http.*

interface UserAuthApi {

    @Headers("Content-Type: application/json")
    @POST("/v1/auth/login")
    fun userLogin(
        @Body loginUser: LoginUser
    ): Flowable<JsonElement>

    @Headers("Content-Type: application/json")
    @POST("/v1/user")
    fun userSignUp(
        @Body signUpUser: SignUpUser
    ): Flowable<JsonElement>

    @Headers("Content-Type: application/json")
    @POST("/v1/user")
    fun userSignUp(
        @Body signUpUser: SignUpUserReceiver
    ): Flowable<JsonElement>

    @GET("/v1/user/validate-nickname")
    suspend fun userValidateNickName(@Query("nickname") nickname: String)

    @GET("/v1/user/validate-nickname")
    suspend fun userValidateNickName(@Query("nickname") nickname: String, @Query("previousNickname") previousNickname: String)
}
