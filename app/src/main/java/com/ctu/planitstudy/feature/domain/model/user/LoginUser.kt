package com.ctu.planitstudy.feature.domain.model.user

import com.google.gson.annotations.SerializedName

data class LoginUser(
    @SerializedName("email")
    val email: String
)
