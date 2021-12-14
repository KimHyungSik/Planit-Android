package com.ctu.planitstudy.feature.data.remote.dto

import com.google.gson.JsonObject

object JsonConverter {

    val TAG = "JsonConverter - 로그"

    fun jsonToLoginDto(jsonObject: JsonObject): LoginDto = LoginDto(
        result = jsonObject["result"].asBoolean,
        accessToken = jsonObject["accessToken"].asString,
        refreshToken = jsonObject["refreshToken"].asString
    )

    fun jsonToSignUpUserDto(jsonObject: JsonObject): SignUpUserDto = SignUpUserDto(
        accessToken = jsonObject["accessToken"].asString,
        refreshToken = jsonObject["refreshToken"].asString
    )
}
