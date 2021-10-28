package com.ctu.planitstudy.feature.data.remote.dto

data class SignUpUserDto(
    val accessToken: String,
    val birth: String,
    val category: String,
    val email: String,
    val id: Int,
    val name: String,
    val nickname: String,
    val refreshToken: String,
    val sex: String
)

fun SignUpUserDto.getRefreshToken() : String = this.refreshToken
fun SignUpUserDto.getAccessToken() : String = this.accessToken