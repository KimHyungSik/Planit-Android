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
            if (it.data!!) {
                binding.signUpNicknameErrorIcon.visibility = View.INVISIBLE
                binding.signUpNicknameErrorText.visibility = View.VISIBLE
                binding.signUpNicknameErrorText.text = "사용 가능한 닉네임 입니다."
            } else {
                binding.signUpNicknameErrorIcon.visibility = View.VISIBLE
                when (it) {
                    is Resource.Success -> {
                        binding.signUpNicknameErrorText.text = "이미 사용중인 닉네임입니다"
                    }
                    is Resource.Error -> {
                        binding.signUpNicknameErrorText.text = "닉네임은 8글자 이내로 입력해주세요."
                    }
                    is Resource.Loading -> {
                        binding.signUpNicknameErrorText.visibility = View.INVISIBLE
                        binding.signUpNicknameErrorIcon.visibility = View.INVISIBLE
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