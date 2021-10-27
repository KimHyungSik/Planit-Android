package com.ctu.cashstudy.feature.presentation.sign_up.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ctu.cashstudy.R
import com.ctu.cashstudy.core.base.BaseFragment
import com.ctu.cashstudy.databinding.FragmentSignUpUserCategoryBinding
import com.ctu.cashstudy.databinding.FragmentSignUpUserDateofbirthBinding
import com.ctu.cashstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import io.reactivex.disposables.CompositeDisposable

class SignUserCategory : BaseFragment<FragmentSignUpUserCategoryBinding>() {

    val TAG = "Category - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserCategoryBinding
        get() = FragmentSignUpUserCategoryBinding::inflate

    private val viewModel : SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()

    override fun setInit() {
        super.setInit()

        disposables.add(
            RxRadioGroup.checkedChanges(binding.signUpCategoryRadioGroupLeft)
                .filter { it -> it != -1 }
                .subscribe({
                    Log.d(TAG, "setInit: $it")
                    binding.signUpCategoryRadioGroupRight.clearCheck()
                    var state = viewModel.liveData.value
                    when(it){
                        R.id.sign_up_category_radio_elementary_school -> state = state!!.copy(category = "elementarySchool")
                        R.id.sign_up_category_radio_high_school -> state = state!!.copy(category = "highSchool")
                        R.id.sign_up_category_radio_college -> state = state!!.copy(category = "college")
                        R.id.sign_up_category_radio_job_seeker -> state = state!!.copy(category = "jobSeeker")
                    }
                    viewModel.updateSignState(state!!)
                }, {})
        )
        disposables.add(
            RxRadioGroup.checkedChanges(binding.signUpCategoryRadioGroupRight)
                .filter { it -> it != -1 }
                .subscribe({
                    Log.d(TAG, "setInit: $it")
                    var state = viewModel.liveData.value
                    binding.signUpCategoryRadioGroupLeft.clearCheck()
                    when(it){
                        R.id.sign_up_category_radio_middle_school -> state = state!!.copy(category = "middleSchool")
                        R.id.sign_up_category_radio_repeater -> state = state!!.copy(category = "repeater")
                        R.id.sign_up_category_radio_civil_service_exam -> state = state!!.copy(category = "serviceExam")
                        R.id.sign_up_category_radio_workers -> state = state!!.copy(category = "workers")
                    }
                    viewModel.updateSignState(state!!)
                }, {})
        )
    }
}