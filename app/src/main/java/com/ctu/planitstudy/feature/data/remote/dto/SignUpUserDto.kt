package com.ctu.planitstudy.feature.data.remote.dto

import com.ctu.planitstudy.feature.domain.model.user.SignUpUserResponse
import com.google.gson.annotations.SerializedName

data class SignUpUserDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("sex")
    val sex: String,
    @SerializedName("birth")
    val birth: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)

fun SignUpUserDto.getRefreshToken(): String = this.refreshToken
fun SignUpUserDto.getAccessToken(): String = this.accessToken

fun SignUpUserDto.toSignUpUserResponse(): SignUpUserResponse =
    SignUpUserResponse(accessToken = this.accessToken, refreshToken = this.refreshToken)
