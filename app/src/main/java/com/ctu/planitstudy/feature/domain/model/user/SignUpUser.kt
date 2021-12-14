package com.ctu.planitstudy.feature.domain.model.user

import com.google.gson.annotations.SerializedName

data class SignUpUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("sex")
    val sex: String?,
    @SerializedName("birth")
    val birth: String?,
    @SerializedName("category")
    val category: String,
    @SerializedName("personalInformationAgree")
    val personalInformationAgree: Boolean,
    @SerializedName("marketingInformationAgree")
    val marketingInformationAgree: Boolean
)

data class SignUpUserReceiver(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("sex")
    val sex: String?,
    @SerializedName("birth")
    val birth: String?,
    @SerializedName("category")
    val category: String,
    @SerializedName("personalInformationAgree")
    val personalInformationAgree: Boolean,
    @SerializedName("marketingInformationAgree")
    val marketingInformationAgree: Boolean,
    @SerializedName("receiverNickname")
    val receiverNickname: String
)
