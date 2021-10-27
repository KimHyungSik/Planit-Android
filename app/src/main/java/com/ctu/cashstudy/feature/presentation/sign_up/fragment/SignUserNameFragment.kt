package com.ctu.cashstudy.feature.presentation.sign_up.fragment

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.ctu.cashstudy.core.base.BaseFragment
import com.ctu.cashstudy.databinding.FragmentSignUpUserNameBinding
import com.ctu.cashstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable

class SignUserNameFragment : BaseFragment<FragmentSignUpUserNameBinding>() {

    val TAG = "NameFragment - 로그"

    override fun getViewBinding(): FragmentSignUpUserNameBinding = FragmentSignUpUserNameBinding.inflate(layoutInflater)

    private val viewModel : SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()

    override fun setUpViews() {
        super.setUpViews()
    }

    override fun setInit() {
        super.setInit()
        disposables.add(RxTextView.textChanges(binding.signUpNameEditText)
            .subscribe({
                val state = viewModel.liveData.value!!.copy(
                    name = it.toString()
                )
                viewModel.updateSignState(state)
            }, {
                Log.e(TAG, "setInit: ${it}", )
            })
        )
        disposables.add(RxTextView.textChanges(binding.signUpNicknameEditText)
            .subscribe({
                val state = viewModel.liveData.value!!.copy(
                    nickname = it.toString()
                )
                viewModel.updateSignState(state)
            }, {
                Log.e(TAG, "setInit: nickname text change ${it.localizedMessage}", )
            })
        )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}