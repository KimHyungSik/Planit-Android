package com.ctu.cashstudy.feature.presentation.sign_up

import android.util.TypedValue
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ctu.cashstudy.R
import com.ctu.cashstudy.core.base.BaseBindingActivity
import com.ctu.cashstudy.databinding.ActivitySignUpScreenBinding
import com.ctu.cashstudy.feature.presentation.sign_up.view_pager.SignFragmentStateAdapter
import android.graphics.Color

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
    }

    fun Int.dpToPixels():Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),this@SignUpScreen.resources.displayMetrics
    )

}