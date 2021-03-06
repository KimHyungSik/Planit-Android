package com.ctu.planitstudy.feature.presentation.sign_up.fragment

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentSignUpUserGenderBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import io.reactivex.disposables.CompositeDisposable

class SignUserGenderFragment : BaseFragment<FragmentSignUpUserGenderBinding, SignUpViewModel>() {

    val TAG = "GenderFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserGenderBinding
        get() = FragmentSignUpUserGenderBinding::inflate

    override val viewModel: SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()
    override fun setInit() {
        super.setInit()

        disposables.add(
            RxRadioGroup.checkedChanges(binding.signUpGenderRadioGroup)
                .subscribe(
                    {
                        var state = viewModel.liveData.value!!.copy()
                        when (it) {
                            R.id.sign_up_gender_male ->
                                state = viewModel.liveData.value!!.copy(gender = "MALE")
                            R.id.sign_up_gender_female ->
                                state = viewModel.liveData.value!!.copy(gender = "FEMALE")
                        }
                        viewModel.isSkip = false
                        viewModel.updateSignState(state)
                    },
                    {
                        Log.e(TAG, "setInit: $it",)
                    }
                )
        )

        binding.signUpSkipEnterUser.setOnClickListener {
            viewModel.skipSignUpFragment()
        }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
