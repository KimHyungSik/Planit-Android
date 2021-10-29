package com.ctu.planitstudy.feature.presentation.sign_up.fragment


import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentSignUpUserDateofbirthBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpScreen
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import io.reactivex.disposables.CompositeDisposable


class SignUserDateOfBirthFragment : BaseFragment<FragmentSignUpUserDateofbirthBinding>() {

    val TAG = "BirthFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserDateofbirthBinding
        get() = FragmentSignUpUserDateofbirthBinding::inflate

    private val viewModel : SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()

    override fun setInit() {
        super.setInit()
        binding.signUpDateOfBirthCardView.setOnClickListener {
            viewModel.datePickerActivityState(true)
        }
        viewModel.liveData.observeForever {
            binding.signUpDateOfBirthEditText.text = it.dateOfBirth
        }
    }

}