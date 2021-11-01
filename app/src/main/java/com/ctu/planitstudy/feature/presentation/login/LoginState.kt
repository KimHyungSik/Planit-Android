package com.ctu.planitstudy.feature.presentation.login

sealed class LoginState(val state: Boolean){
    class Login(_state : Boolean) : LoginState(_state)
    class Loading(_state : Boolean) : LoginState(_state)
}
