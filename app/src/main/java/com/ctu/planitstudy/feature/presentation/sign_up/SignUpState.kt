package com.ctu.planitstudy.feature.presentation.sign_up

data class SignUpState(
    val name: String = "",
    val nickname: String = "",
    val gender: String = "",
    val dateFormat: Boolean = false,
    val dateOfBirth: String = "",
    val category: String = "",
    val receiverName: String = ""
)
