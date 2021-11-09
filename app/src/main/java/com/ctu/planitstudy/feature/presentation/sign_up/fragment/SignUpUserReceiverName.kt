package com.ctu.planitstudy.feature.presentation.sign_up.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentSignUpUserReceiverNameBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable

class SignUpUserReceiverName : BaseFragment<FragmentSignUpUserReceiverNameBinding>() {

    val TAG = "ReceiverName - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserReceiverNameBinding
        get() = FragmentSignUpUserReceiverNameBinding::inflate

    private val viewModel: SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()

    override fun setInit() {
        super.setInit()

        disposables.add(
            RxTextView.textChanges(binding.signUpReceiverNameEditText)
                .subscribe({
                    val state = viewModel.liveData.value!!.copy(
                        receiverName = it.toString()
                    )
                    viewModel.updateSignState(state)
                }, {})
        )

        viewModel.signUpUserResponse.observe(this, {
            Log.d(TAG, "setInit: $it")
            when (it.responseCode) {
                404 -> {
                    binding.signUpReceiverNameErrorIcon.visibility = View.VISIBLE
                    binding.signUpReceiverNameErrorText.visibility = View.VISIBLE
                    binding.signUpReceiverNameErrorText.text = "추천인이 존재 하지 않습니다."
                }
                409 -> {
                }
            }
        })

    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}