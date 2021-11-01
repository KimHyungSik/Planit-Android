package com.ctu.planitstudy.feature.presentation.util

import com.ctu.planitstudy.MainActivity
import com.ctu.planitstudy.feature.presentation.login.LoginScreen
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpScreen
import com.ctu.planitstudy.feature.presentation.terms_of_use.TermsOfUseAgreesScreen

sealed class Screens(val activity: Class<*>){
    class MainScreenSh(): Screens(MainActivity::class.java)
    class LoginScreenSh(): Screens(LoginScreen::class.java)
    class TermsOfUseAgreesScreenSh: Screens(TermsOfUseAgreesScreen::class.java)
    class SignUpScreenSh(): Screens(SignUpScreen::class.java)
}
