package com.ctu.planitstudy.feature.data.remote.dto

import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
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

    fun jsonToDdayListDto(jsonObject: JsonObject): DdayListDto {
        val ddayList = ArrayList<DdayDto>()
        for (n in jsonObject["ddays"].asJsonArray) {
            val json = n.asJsonObject
            ddayList.add(
                jsonToDdayDto(json)
            )
        }
        return DdayListDto(ddayList)
    }

    fun jsonToDdayDto(jsonObject: JsonObject): DdayDto = DdayDto(
        id = jsonObject["id"].asInt,
        title = jsonObject["title"].asString,
        isRepresentative = jsonObject["isRepresentative"].asBoolean,
        endAt = jsonObject["endAt"].asString,
        createdAt = jsonObject["createdAt"].asString,
        color = jsonObject["color"].asString,
        dDay = DateCalculation().calDateBetween(
            DateCalculation().getCurrentDateString(0),
            jsonObject["endAt"].asString
        )
    )
}