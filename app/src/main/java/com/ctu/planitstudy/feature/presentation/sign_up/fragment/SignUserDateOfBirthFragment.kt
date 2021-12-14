package com.ctu.planitstudy.feature.presentation.sign_up.fragment

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentSignUpUserDateofbirthBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import java.util.regex.Pattern

class SignUserDateOfBirthFragment : BaseFragment<FragmentSignUpUserDateofbirthBinding, SignUpViewModel>() {

    val TAG = "BirthFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserDateofbirthBinding
        get() = FragmentSignUpUserDateofbirthBinding::inflate

    override val viewModel: SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()
    var textCount = 0

    override fun setInit() {
        super.setInit()
        disposables.add(
            RxTextView.textChanges(binding.signUpDateOfBirthEditText)
                .map {
                    TextWatcher(
                        Pattern.matches("^((19|20)\\d\\d)[-|/](0[1-9]|1[012])[-|/](0[1-9]|[12][0-9]|3[01])\$", it.toString()),
                        it.toString()
                    )
                }
                .subscribe {
                    // 문자열 증가 중
                    if (textCount < it.text.length) {
                        if (it.text.length == 4 || it.text.length == 7) {
                            it.text += '-'
                            binding.signUpDateOfBirthEditText.setText(it.text)
                            binding.signUpDateOfBirthEditText.setSelection(it.text.length)
                        }
                    }
                    textCount = it.text.length
                    viewModel.updateSignState(
                        viewModel.liveData.value!!.copy(
                            dateOfBirth = it.text,
                            dateFormat = it.dateFormat
                        )
                    )
                    viewModel.isSkip = false
                }
        )
        binding.signUpSkipEnterUser.setOnClickListener {
            viewModel.skipSignUpFragment()
        }
    }

    data class TextWatcher(
        val dateFormat: Boolean,
        var text: String
    )
}
