package com.ctu.planitstudy.feature.presentation.login

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityLoginScreenBinding
import com.ctu.planitstudy.feature.presentation.util.Screens.HomeScreenSh
import com.ctu.planitstudy.feature.presentation.util.Screens.TermsOfUseAgreesScreenSh
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen :
    BaseBindingActivity<ActivityLoginScreenBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityLoginScreenBinding =
        ActivityLoginScreenBinding::inflate

    val TAG = "LoginScreen - 로그"

    override fun setup() {

        binding.viewmodel = viewModel

        viewModel.loginState.observe(
            this,
            {
                when (it) {
                    is LoginState.Loading -> {
                    }
                    is LoginState.Login -> {
                        if (it.state) {
                            moveIntentAllClear(HomeScreenSh.activity)
                        } else
                            moveIntentAllClear(TermsOfUseAgreesScreenSh.activity)
                    }
                }
            }
        )
    }
}
