package com.ctu.planitstudy.feature.data.remote.dto.user

import com.google.gson.annotations.SerializedName

data class UserInformation(
    @SerializedName("birth")
    val birth: String,
    @SerializedName("birth")
    val category: String,
    @SerializedName("birth")
    val email: String,
    @SerializedName("birth")
    val id: Int,
    @SerializedName("birth")
    val name: String,
    @SerializedName("birth")
    val nickname: String,
    @SerializedName("birth")
    val sex: String
)