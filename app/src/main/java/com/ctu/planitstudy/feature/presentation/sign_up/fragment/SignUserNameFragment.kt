package com.ctu.planitstudy.feature.presentation.sign_up.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentSignUpUserNameBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable

class SignUserNameFragment : BaseFragment<FragmentSignUpUserNameBinding>() {

    val TAG = "NameFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserNameBinding
        get() = FragmentSignUpUserNameBinding::inflate

    private val viewModel: SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()

    override fun setUpViews() {
        super.setUpViews()
    }

    override fun setInit() {
        super.setInit()
        disposables.add(
            RxTextView.textChanges(binding.signUpNameEditText)
                .subscribe({
                    val state = viewModel.liveData.value!!.copy(
                        name = it.toString()
                    )
                    viewModel.updateSignState(state)
                }, {
                })
        )
        disposables.add(
            RxTextView.textChanges(binding.signUpNicknameEditText)
                .subscribe({
                    Log.d(TAG, "setInit: ")
                    val state = viewModel.liveData.value!!.copy(
                        nickname = it.toString()
                    )
                    viewModel.updateSignState(state)
                    viewModel.validateNickNameStateChange(false)
                }, {
                })
        )

        viewModel.validateNickName.observe(this, {
            with(binding) {
                if (it.data!!) {
                    signUpNicknameErrorIcon.visibility = View.INVISIBLE
                    signUpNicknameErrorText.visibility = View.VISIBLE
                    signUpNicknameErrorText.text = "사용 가능한 닉네임 입니다."
                } else {

                    signUpNicknameErrorText.visibility = View.VISIBLE
                    when (it) {
                        is Resource.Success -> {
                            signUpNicknameErrorIcon.visibility = View.VISIBLE
                            signUpNicknameErrorText.text = "이미 사용중인 닉네임입니다"
                        }
                        is Resource.Error -> {
                            signUpNicknameErrorText.text = ""
                        }
                        is Resource.Loading -> {
                            signUpNicknameErrorText.visibility = View.INVISIBLE
                            signUpNicknameErrorIcon.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
