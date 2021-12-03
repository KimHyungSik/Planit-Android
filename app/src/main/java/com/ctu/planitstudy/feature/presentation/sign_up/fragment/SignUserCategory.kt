package com.ctu.planitstudy.feature.presentation.sign_up.fragment

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.enums.findCategoryFromView
import com.ctu.planitstudy.databinding.FragmentSignUpUserCategoryBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import io.reactivex.disposables.CompositeDisposable

class SignUserCategory : BaseFragment<FragmentSignUpUserCategoryBinding, SignUpViewModel>() {

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserCategoryBinding
        get() = FragmentSignUpUserCategoryBinding::inflate

    override val viewModel: SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()

    override fun setInit() {
        super.setInit()

        disposables.add(
            RxRadioGroup.checkedChanges(binding.signUpCategoryRadioGroupLeft)
                .filter { it -> it != -1 }
                .subscribe({
                    binding.signUpCategoryRadioGroupRight.clearCheck()
                    var state = viewModel.liveData.value
                    state = state!!.copy(category = findCategoryFromView(it)!!.category)
                    viewModel.updateSignState(state!!)
                }, {})
        )
        disposables.add(
            RxRadioGroup.checkedChanges(binding.signUpCategoryRadioGroupRight)
                .filter { it -> it != -1 }
                .subscribe({
                    binding.signUpCategoryRadioGroupLeft.clearCheck()
                    var state = viewModel.liveData.value
                    state = state!!.copy(category = findCategoryFromView(it)!!.category)
                    viewModel.updateSignState(state!!)
                }, {})
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
