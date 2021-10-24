package com.ctu.cashstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ctu.cashstudy.core.base.BaseActivity
import com.ctu.cashstudy.feature.presentation.login.LoginScreen
import com.ctu.cashstudy.feature.presentation.login.LoginState

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moveIntent(LoginScreen::class.java)
    }
}