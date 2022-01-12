package com.ctu.planitstudy.feature.data.remote

import android.provider.Settings.Global.getString
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.util.CoreData.APP_VERSION
import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.data.remote.dto.SignUpUserDto
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUser
import com.ctu.planitstudy.feature.domain.model.user.SignUpUserReceiver
import retrofit2.http.*

interface UserAuthApi {

    @POST("/v1/auth/login/")
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
