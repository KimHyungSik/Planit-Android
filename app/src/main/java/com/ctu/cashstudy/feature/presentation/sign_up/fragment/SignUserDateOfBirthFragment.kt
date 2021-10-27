package com.ctu.cashstudy.feature.presentation.sign_up.fragment


import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.ctu.cashstudy.core.base.BaseFragment
import com.ctu.cashstudy.databinding.FragmentSignUpUserDateofbirthBinding
import com.ctu.cashstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable

class SignUserDateOfBirthFragment : BaseFragment<FragmentSignUpUserDateofbirthBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserDateofbirthBinding
        get() = FragmentSignUpUserDateofbirthBinding::inflate

    private val viewModel : SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()

    override fun setInit() {
        super.setInit()
        disposables.add(RxTextView.textChanges(binding.signUpDateOfBirthEditText)
            .subscribe({
                val state = viewModel.liveData.value!!.copy(
                    dateOfBirth = it.toString()
                )
                viewModel.updateSignState(state)
            },{

            }
            )
        )
    }

}