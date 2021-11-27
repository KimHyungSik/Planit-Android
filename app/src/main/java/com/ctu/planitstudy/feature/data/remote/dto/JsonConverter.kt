package com.ctu.planitstudy.feature.data.remote.dto

import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.google.gson.JsonArray
import com.google.gson.JsonObject

object JsonConverter {

    val TAG = "JsonConverter - 로그"

    fun jsonToLoginDto(jsonObject: JsonObject): LoginDto = LoginDto(
        result = jsonObject["result"].asBoolean,
        accessToken = jsonObject["accessToken"].asString,
        refreshToken = jsonObject["refreshToken"].asString
    )

    fun jsonToSignUpUserDto(jsonObject: JsonObject): SignUpUserDto = SignUpUserDto(
        id = jsonObject["id"].asInt,
        email = jsonObject["email"].asString,
        name = jsonObject["name"].asString,
        nickname = jsonObject["nickname"].asString,
        sex = jsonObject["sex"].asString,
        birth = jsonObject["birth"].asString,
        category = jsonObject["category"].asString,
        accessToken = jsonObject["accessToken"].asString,
        refreshToken = jsonObject["refreshToken"].asString
    )

}
