package com.ctu.planitstudy.feature.presentation.sign_up.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.isValidText
import com.ctu.planitstudy.databinding.FragmentSignUpUserNameBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SignUserNameFragment : BaseFragment<FragmentSignUpUserNameBinding, SignUpViewModel>() {

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserNameBinding
        get() = FragmentSignUpUserNameBinding::inflate

    override val viewModel: SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()

    override fun setInit() {
        super.setInit()
        disposables.addAll(
            RxTextView.textChanges(binding.signUpNameEditText)
                .subscribe(
                    {
                        val state = viewModel.liveData.value!!.copy(
                            name = it.toString(),
                            nameCheck = it.toString().isValidText()
                        )
                        viewModel.updateSignState(state)
                    },
                    {
                    }
                ),
            RxTextView.textChanges(binding.signUpNicknameEditText)
                .filter { it.isNotBlank() }
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe(
                    {
                        viewModel.validateNickNameCheck()
                    },
                    {
                    }
                ),
            RxTextView.textChanges(binding.signUpNicknameEditText)
                .subscribe(
                    {
                        val state = viewModel.liveData.value!!.copy(
                            nickname = it.toString(),
                            nicknameCheck = it.toString()
                                .isValidText() && viewModel.validateNickName.value!!.data == true
                        )
                        viewModel.updateSignState(state)
                        viewModel.validateNickNameStateChange(false)
                    },
                    {
                    }
                )
        )

        viewModel.validateNickName.observe(
            this,
            {
                with(binding) {
                    if (it.data!!) {
                        signUpNicknameErrorIcon.visibility = View.INVISIBLE
                        signUpNicknameErrorText.visibility = View.VISIBLE
                        signUpNicknameErrorText.text = "?????? ????????? ????????? ?????????."
                        val state = viewModel.liveData.value!!.copy(
                            nicknameCheck = true
                        )
                        viewModel.updateSignState(state)
                    } else {
                        signUpNicknameErrorText.visibility = View.VISIBLE
                        when (it) {
                            is Resource.Success -> {
                                signUpNicknameErrorIcon.visibility = View.VISIBLE
                                signUpNicknameErrorText.text = "?????? ???????????? ??????????????????"
                            }
                            is Resource.Error -> {
                                signUpNicknameErrorText.text = ""
                            }
                            is Resource.Loading -> {
                                signUpNicknameErrorText.visibility = View.INVISIBLE
                                signUpNicknameErrorIcon.visibility = View.INVISIBLE
                            }
                        }
                        val state = viewModel.liveData.value!!.copy(
                            nicknameCheck = false
                        )
                        viewModel.updateSignState(state)
                    }
                }
            }
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
