package com.ctu.cashstudy.feature.presentation.sign_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ctu.cashstudy.core.base.BaseBindingActivity
import com.ctu.cashstudy.databinding.ActivitySignUpScreenBinding
import com.ctu.cashstudy.feature.presentation.sign_up.view_pager.SignFragmentStateAdapter

class SignUpScreen
    : BaseBindingActivity<ActivitySignUpScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySignUpScreenBinding
            = ActivitySignUpScreenBinding::inflate

    override fun setup() {
        binding.signUpViewpager.apply {
            adapter = SignFragmentStateAdapter(this@SignUpScreen)
            currentItem = 0
            isUserInputEnabled = false
        }
        binding.signUpIndicator.setViewPager2(binding.signUpViewpager)

    }

}