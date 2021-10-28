package com.ctu.planitstudy.feature.presentation.sign_up.fragment

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentSignUpUserCategoryBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
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
                        R.id.sign_up_category_radio_elementary_school -> state = state!!.copy(category = "ELEMENTARY_SCHOOL")
                        R.id.sign_up_category_radio_high_school -> state = state!!.copy(category = "HIGH_SCHOOL")
                        R.id.sign_up_category_radio_college -> state = state!!.copy(category = "UNIVERSITY")
                        R.id.sign_up_category_radio_job_seeker -> state = state!!.copy(category = "JOB_PREP")
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
                        R.id.sign_up_category_radio_middle_school -> state = state!!.copy(category = "MIDDLE_SCHOOL")
                        R.id.sign_up_category_radio_repeater -> state = state!!.copy(category = "NTH_EXAM")
                        R.id.sign_up_category_radio_civil_service_exam -> state = state!!.copy(category = "serviceExam")
                        R.id.sign_up_category_radio_workers -> state = state!!.copy(category = "WORKER")
                    }
                    viewModel.updateSignState(state!!)
                }, {})
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}