package com.ctu.cashstudy.feature.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val isLogin: Boolean = false,
    val error: String = ""
)
