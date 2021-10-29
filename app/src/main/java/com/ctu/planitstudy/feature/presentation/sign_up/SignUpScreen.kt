package com.ctu.planitstudy.feature.presentation.sign_up

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivitySignUpScreenBinding
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.SignUpFragments
import com.ctu.planitstudy.feature.presentation.sign_up.view_pager.SignFragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.terms_of_use.TermsOfUseAgrees
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SignUpScreen
    : BaseBindingActivity<ActivitySignUpScreenBinding>() {

    val TAG = "SignUpScreen - 로그"

    private val viewModel : SignUpViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivitySignUpScreenBinding
            = ActivitySignUpScreenBinding::inflate

    private val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()

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
        if(termsOfUseAgrees != null)
            viewModel.termsOfUseAgrees = termsOfUseAgrees

        datePicker.addOnPositiveButtonClickListener {
            val timeZoneUTC: TimeZone = TimeZone.getDefault()
            val offsetFromUTC: Int = timeZoneUTC.getOffset(Date().getTime()) * -1
            val simpleFormat = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA)
            val date = Date(it + offsetFromUTC)

            viewModel.updateSignState(viewModel.liveData.value!!.copy(dateOfBirth = simpleFormat.format(date)))
        }
    }

    private fun viewModelObserveSetUp(){

        viewModel.activityState.observe(this, Observer {
            if (it) {
                binding.signUpBtn.run {
                    setCardBackgroundColor(resources.getColor(R.color.white))
                    isClickable = true
                }
            } else {
                binding.signUpBtn.run {
                    setCardBackgroundColor(resources.getColor(R.color.button_disabled))
                    isClickable = false
                }
            }
            if (viewModel.signUpFragments.value == SignUpFragments.ReceiverName)
                setReceiverUi(it)
        })

        viewModel.signUpFragments.observe(this, {
            if (viewModel.signUpFragments.value == SignUpFragments.ReceiverName)
                setReceiverUi(false)
            binding.signUpViewpager.currentItem = it.page
        })

        viewModel.datePickerActivity.observe(this, {
            if (it) {
                datePicker.show(this.supportFragmentManager, "datePicker")
                viewModel.datePickerActivityState(false)
            }
        })
    }

    fun setReceiverUi(state: Boolean){
        if(!state){
            binding.signUpBtn.run {
                setCardBackgroundColor(resources.getColor(R.color.button_disabled))
                isClickable = true
            }
            binding.signUpBtnText.run{
                text = "건너뛰기"
                setTextColor(resources.getColor(R.color.white))
            }
        }else{
            binding.signUpBtn.run {
                setCardBackgroundColor(resources.getColor(R.color.white))
                isClickable = true
            }
            binding.signUpBtnText.run{
                binding.signUpBtnText.text = "확인"
                setTextColor(resources.getColor(R.color.black))
            }
        }
    }

}