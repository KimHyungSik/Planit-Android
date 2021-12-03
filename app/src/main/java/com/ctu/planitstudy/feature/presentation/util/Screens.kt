package com.ctu.planitstudy.feature.presentation.util

import com.ctu.planitstudy.feature.presentation.dday.DdayScreen
import com.ctu.planitstudy.feature.presentation.edituser.EditUserScreen
import com.ctu.planitstudy.feature.presentation.home.HomeScreen
import com.ctu.planitstudy.feature.presentation.login.LoginScreen
import com.ctu.planitstudy.feature.presentation.measurement.MeasurementScreen
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpScreen
import com.ctu.planitstudy.feature.presentation.study.StudyScreen
import com.ctu.planitstudy.feature.presentation.terms_of_use.TermsOfUseAgreesScreen
import com.ctu.planitstudy.feature.presentation.terms_of_use.webview.TermsOfUse
import com.ctu.planitstudy.feature.presentation.timer.TimerScreen

sealed class Screens(val activity: Class<*>) {
    object LoginScreenSh : Screens(LoginScreen::class.java)
    object TermsOfUseAgreesScreenSh : Screens(TermsOfUseAgreesScreen::class.java)
    object TermsOfUseSh : Screens(TermsOfUse::class.java)
    object HomeScreenSh : Screens(HomeScreen::class.java)
    object SignUpScreenSh : Screens(SignUpScreen::class.java)
    object DdayScreenSh : Screens(DdayScreen::class.java)
    object StudyScreenSh : Screens(StudyScreen::class.java)
    object TimerScreenSh : Screens(TimerScreen::class.java)
    object MeasurementScreenSh : Screens(MeasurementScreen::class.java)
    object EditUserScreenSh : Screens(EditUserScreen::class.java)
}
