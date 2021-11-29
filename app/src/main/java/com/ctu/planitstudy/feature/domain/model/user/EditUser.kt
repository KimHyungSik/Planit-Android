package com.ctu.planitstudy.feature.domain.model.user

import com.google.gson.annotations.SerializedName

data class EditUser(
    @SerializedName("category")
    val category: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String
)
