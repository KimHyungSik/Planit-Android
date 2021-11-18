package com.ctu.planitstudy.feature.presentation.login

import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.CoreData.ACCESSTOKEN
import com.ctu.planitstudy.core.util.PreferencesManager
import com.ctu.planitstudy.core.util.network.JWTRefreshTokenExpiration
import com.ctu.planitstudy.databinding.ActivityLoginScreenBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.home.HomeScreen
import com.ctu.planitstudy.feature.presentation.util.Screens.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen
    : BaseBindingActivity<ActivityLoginScreenBinding>()
{
    private val viewModel: LoginViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityLoginScreenBinding
        = ActivityLoginScreenBinding::inflate

    val TAG = "LoginScreen - 로그"

    override fun setup() {

        binding.viewmodel = viewModel

        if(CashStudyApp.prefs.refreshToken != null)
            if(!JWTRefreshTokenExpiration().invoke())
                moveIntentAllClear(HomeScreenSh.activity)

        viewModel.loginState.observe(this, {
            when(it){
                is LoginState.Loading->{
                    Toast.makeText(this, "로그인 중", Toast.LENGTH_SHORT).show()
                }
                is LoginState.Login->{
                    if(it.state) {
                        moveIntentAllClear(HomeScreenSh.activity)
                    }
                    else
                        moveIntentAllClear(TermsOfUseAgreesScreenSh.activity)
                }
            }
        })
    }

}