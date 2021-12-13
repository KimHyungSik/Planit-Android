package com.ctu.planitstudy.feature.presentation.sign_up

import com.ctu.planitstudy.core.util.enums.CategoryEnums

data class SignUpState(
    val name: String = "",
    val nameCheck: Boolean = false,
    val nickname: String = "",
    val nicknameCheck: Boolean = false,
    val gender: String = "",
    val dateFormat: Boolean = false,
    val dateOfBirth: String = "",
    val category: String = "",
    val receiverName: String = ""
)
