package com.ctu.cashstudy.feature.presentation.sign_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Observer
import com.ctu.cashstudy.R
import com.ctu.cashstudy.core.base.BaseBindingActivity
import com.ctu.cashstudy.databinding.ActivitySignUpScreenBinding
import com.ctu.cashstudy.feature.presentation.sign_up.view_pager.SignFragmentStateAdapter

class SignUpScreen
    : BaseBindingActivity<ActivitySignUpScreenBinding>() {

    private val viewModel : SignUpViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivitySignUpScreenBinding
            = ActivitySignUpScreenBinding::inflate

    override fun setup() {
        binding.signUpViewpager.apply {
            adapter = SignFragmentStateAdapter(this@SignUpScreen)
            currentItem = 0
            isUserInputEnabled = false
        }
        binding.signUpIndicator.setViewPager2(binding.signUpViewpager)

        viewModel.liveData.observe(this, Observer {
            if(!it.name.isNullOrEmpty() && !it.nickname.isNullOrEmpty())
                binding.signUpBtn.setBackgroundColor(resources.getColor(R.color.white))
        })
    }

}