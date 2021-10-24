package com.ctu.cashstudy.feature.presentation.login

import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ctu.cashstudy.core.base.BaseBindingActivity
import com.ctu.cashstudy.databinding.ActivityLoginScreenBinding
import com.ctu.cashstudy.feature.presentation.util.ActivityLifeCycleObserver
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
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
        Log.d(TAG, "setup: ${viewModel.hashCode()}")
    }

}