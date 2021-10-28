package com.ctu.planitstudy.feature.presentation.login

import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.ctu.planitstudy.MainActivity
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityLoginScreenBinding
import com.ctu.planitstudy.feature.presentation.terms_of_use.TermsOfUseScreenAgrees
import com.ctu.planitstudy.feature.presentation.util.ActivityLifeCycleObserver
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

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
        viewModel.loginState.observe(this, {
            when(it){
                is LoginState.Loading->{}
                is LoginState.Login->{
                    if(it.state)
                        moveIntent(MainActivity::class.java)
                    else
                        moveIntent(TermsOfUseScreenAgrees::class.java)
                }
            }
        })
    }

}