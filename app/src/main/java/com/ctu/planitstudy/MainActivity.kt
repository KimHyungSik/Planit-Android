package com.ctu.planitstudy

import android.os.Bundle
import com.ctu.planitstudy.core.base.BaseActivity
import com.ctu.planitstudy.feature.presentation.login.LoginScreen

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moveIntent(LoginScreen::class.java)
    }
}