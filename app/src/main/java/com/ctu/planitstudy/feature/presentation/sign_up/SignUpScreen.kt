package com.ctu.planitstudy.feature.presentation.sign_up

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.setColor
import com.ctu.planitstudy.databinding.ActivitySignUpScreenBinding
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.SignUpFragments
import com.ctu.planitstudy.feature.presentation.sign_up.view_pager.SignFragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.terms_of_use.TermsOfUseAgrees
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SignUpScreen :
    BaseBindingActivity<ActivitySignUpScreenBinding, SignUpViewModel>() {

    val TAG = "SignUpScreen - 로그"

    override val viewModel: SignUpViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivitySignUpScreenBinding =
        ActivitySignUpScreenBinding::inflate

    override fun setup() {
        binding.signUpViewpager.apply {
            adapter = SignFragmentStateAdapter(this@SignUpScreen)
            currentItem = 0
            isUserInputEnabled = false
        }

        binding.signUpIndicator.setViewPager2(binding.signUpViewpager)

        binding.viewmodel = viewModel

        viewModelObserveSetUp()

        val termsOfUseAgrees = intent.getParcelableExtra<TermsOfUseAgrees>("termsOfUseAgrees")
        if (termsOfUseAgrees != null)
            viewModel.termsOfUseAgrees = termsOfUseAgrees
    }

    private fun viewModelObserveSetUp() {

        viewModel.activityState.observe(
            this,
            Observer {
                if (it) {
                    with(binding.signUpBtn) {
                        setCardBackgroundColor(setColor(R.color.white))
                        isClickable = true
                        setOnClickListener {
                            viewModel.checkSignUpUserData()
                        }
                    }
                    with(binding.signUpConfirmBtn) {
                        setCardBackgroundColor(setColor(R.color.white))
                        isClickable = true
                    }
                } else {
                    with(binding.signUpBtn) {
                        setCardBackgroundColor(setColor(R.color.button_disabled))
                        setOnClickListener {
                            Toast.makeText(this@SignUpScreen, "빈칸을 채워 주세요", Toast.LENGTH_SHORT).show()
                        }
                    }
                    with(binding.signUpConfirmBtn) {
                        setCardBackgroundColor(setColor(R.color.button_disabled))
                        isClickable = false
                    }
                }
            }
        )

        viewModel.signUpFragments.observe(this, {
            setReceiverUi(viewModel.signUpFragments.value == SignUpFragments.ReceiverName)
            binding.signUpViewpager.currentItem = it.page
        })

        viewModel.screens.observe(this, {
            if (it != null)
                moveIntentAllClear(it.activity)
        })
    }

    private fun setReceiverUi(state: Boolean) {
        with(binding) {
            if (!state) {
                signUpBtn.visibility = View.VISIBLE
                signUpConfirm.visibility = View.GONE
            } else {
                signUpBtn.visibility = View.GONE
                signUpConfirm.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        if (viewModel.currentFragmentPage > 0) {
            viewModel.fragmentPageChange(-1)
        } else {
            super.onBackPressed()
        }
    }
}
