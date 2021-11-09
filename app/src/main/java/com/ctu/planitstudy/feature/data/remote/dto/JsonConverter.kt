package com.ctu.planitstudy.feature.data.remote.dto

import android.util.Log
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.google.gson.JsonObject
import org.json.JSONObject

object JsonConverter {

    val TAG = "JsonConverter - 로그"

    fun jsonToLoginDto(jsonObject: JsonObject) : LoginDto = LoginDto(
        result = jsonObject["result"].asBoolean,
        accessToken = jsonObject["accessToken"].asString,
        refreshToken = jsonObject["refreshToken"].asString
    )

    fun jsonToSignUpUserDto(jsonObject: JsonObject) : SignUpUserDto = SignUpUserDto(
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

    fun jsonToDdayListDto (jsonObject: JsonObject) : DdayListDto{
        val ddayList = ArrayList<DdayDto>()
        for(n in jsonObject["ddays"].asJsonArray){
            val json = n.asJsonObject
            ddayList.add(
                DdayDto(
                    id = json["id"].asInt,
                    title = json["title"].asString,
                    isRepresentative = json["isRepresentative"].asBoolean,
                    endAt = json["endAt"].asString,
                    createdAt = json["createdAt"].asString,
                    color = json["color"].asString
                )
            )
        }
        return DdayListDto(ddayList)
    }
}