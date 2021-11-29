package com.ctu.planitstudy.feature.data.remote

import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.data.remote.dto.util.MessageDto
import com.ctu.planitstudy.feature.domain.model.user.EditUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApi {

    @GET("/v1/user/validate-nickname")
    suspend fun userValidateNickName(@Query("nickname") nickname: String)

    @GET("/v1/user/validate-nickname")
    suspend fun userValidateNickName(@Query("nickname") nickname: String, @Query("previousNickname") previousNickname: String)

    @GET("/v1/user")
    suspend fun getUser(): UserInformationDto

    @PUT("/v1/user")
    suspend fun editUser(@Body editUser: EditUser): MessageDto
}
