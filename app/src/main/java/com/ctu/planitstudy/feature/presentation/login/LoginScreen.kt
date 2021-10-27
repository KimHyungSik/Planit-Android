package com.ctu.planitstudy.feature.presentation.login

import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.ctu.planitstudy.MainActivity
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityLoginScreenBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpScreen
import com.ctu.planitstudy.feature.presentation.terms_of_use.TermsOfUseScreen
import com.ctu.planitstudy.feature.presentation.util.ActivityLifeCycleObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen
    : BaseBindingActivity<ActivityLoginScreenBinding>()
{
    private val viewModel: LoginViewModel by viewModels()
    private var lifeCycleObserver = ActivityLifeCycleObserver(lifecycle)

    override val bindingInflater: (LayoutInflater) -> ActivityLoginScreenBinding
        = ActivityLoginScreenBinding::inflate

    val TAG = "LoginScreen - 로그"

    override fun setup() {
        lifecycle.addObserver(lifeCycleObserver)
        binding.viewmodel = viewModel
        viewModel.loginState.observe(this, {
            when(it){
                is LoginState.Loading->{}
                is LoginState.Login->{
                    if(it.state)
                        moveIntent(MainActivity::class.java)
                    else
                        moveIntent(TermsOfUseScreen::class.java)
                }
            }
        })
    }

}