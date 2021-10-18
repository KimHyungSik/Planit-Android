package com.ctu.cashstudy.feature.presentation.login

import android.view.LayoutInflater
import com.ctu.cashstudy.core.base.BaseBindingActivity
import com.ctu.cashstudy.databinding.ActivityLoginScreenBinding

class LoginScreen : BaseBindingActivity<ActivityLoginScreenBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityLoginScreenBinding
        get() = ActivityLoginScreenBinding::inflate

    override fun setup() {
    }

}