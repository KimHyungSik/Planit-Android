package com.ctu.cashstudy.feature.presentation.sign_up.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ctu.cashstudy.R
import com.ctu.cashstudy.core.base.BaseFragment
import com.ctu.cashstudy.databinding.FragmentSignUpUserGenderBinding
import com.ctu.cashstudy.feature.presentation.sign_up.SignUpState
import com.ctu.cashstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import io.reactivex.disposables.CompositeDisposable

class SignUserGenderFragment : BaseFragment<FragmentSignUpUserGenderBinding>() {

    val TAG = "GenderFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserGenderBinding
        get() = FragmentSignUpUserGenderBinding::inflate

    private val viewModel: SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()
    override fun setInit() {
        super.setInit()
        disposables.add(
            RxRadioGroup.checkedChanges(binding.signUpGenderRadioGroup)
                .subscribe({
                   var state = viewModel.liveData.value!!.copy()
                    when(it){
                        R.id.sign_up_gender_male ->
                            state = viewModel.liveData.value!!.copy(gender = "male")
                        R.id.sign_up_gender_female ->
                            state = viewModel.liveData.value!!.copy(gender = "female")
                    }
                    viewModel.updateSignState(state)
                }, {
                    Log.e(TAG, "setInit: ${it}", )
                })
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}