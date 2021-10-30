package com.ctu.planitstudy.feature.presentation.sign_up

data class SignUpState (
        val name : String? = null,
        val nickname: String? = null,
        val gender : String? = null,
        val dateFormat : Boolean = false,
        val dateOfBirth : String? = null,
        val category: String? = null,
        val receiverName: String = ""
)
