package com.ctu.planitstudy.feature.presentation.sign_up

import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivitySignUpScreenBinding
import com.ctu.planitstudy.feature.presentation.sign_up.view_pager.SignFragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.terms_of_use.TermsOfUseAgrees

class SignUpScreen
    : BaseBindingActivity<ActivitySignUpScreenBinding>() {

    val TAG = "SignUpScreen - 로그"

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

        binding.viewmodel = viewModel

        viewModel.activityState.observe(this, Observer {
            if(it) {
                binding.signUpBtn.run{
                    setCardBackgroundColor(resources.getColor(R.color.white))
                    isClickable = true
                }
            }else{
                binding.signUpBtn.run{
                    setCardBackgroundColor(resources.getColor(R.color.button_disabled))
                    isClickable = false
                }
            }
        })
        viewModel.signUpFragments.observe(this, {
            binding.signUpViewpager.currentItem = it.page
        })

        val termsOfUseAgrees = intent.getParcelableExtra<TermsOfUseAgrees>("termsOfUseAgrees")
        if(termsOfUseAgrees != null)
            viewModel.termsOfUseAgrees = termsOfUseAgrees
    }

}