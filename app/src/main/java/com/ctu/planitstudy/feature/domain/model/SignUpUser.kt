package com.ctu.planitstudy.feature.domain.model

data class SignUpUser(
    val birth: String,
    val category: String,
    val email: String,
    val marketingInformationAgree: Boolean,
    val name: String,
    val nickname: String,
    val personalInformationAgree: Boolean,
    val receiverNickname: String,
    val sex: String
)