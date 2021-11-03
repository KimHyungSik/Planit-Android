package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.LoginUser
import com.ctu.planitstudy.feature.domain.model.SignUpUser
import com.ctu.planitstudy.feature.domain.model.SignUpUserReceiver
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import io.reactivex.Flowable
import org.json.JSONObject
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
    suspend fun userValidateNickName(@Query("nickname") nickname : String )
}