package com.ctu.planitstudy.feature.presentation.sign_up.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentSignUpUserReceiverNameBinding
import com.ctu.planitstudy.feature.presentation.sign_up.SignUpViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable

class SignUpUserReceiverName : BaseFragment<FragmentSignUpUserReceiverNameBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentSignUpUserReceiverNameBinding
        get() = FragmentSignUpUserReceiverNameBinding::inflate

    private val viewModel: SignUpViewModel by activityViewModels()

    private val disposables = CompositeDisposable()

    override fun setInit() {
        super.setInit()

        disposables.add(
            RxTextView.textChanges(binding.signUpReceiverNameEditText)
                .subscribe({
                    if(it.isNotBlank()){
                        binding.signUpReceiverNameErrorIcon.visibility = View.INVISIBLE
                        binding.signUpReceiverNameErrorText.visibility = View.INVISIBLE
                    }else{
                        binding.signUpReceiverNameErrorIcon.visibility = View.VISIBLE
                        binding.signUpReceiverNameErrorText.visibility = View.VISIBLE
                    }
                    val state = viewModel.liveData.value!!.copy(
                        receiverName = it.toString()
                    )
                    viewModel.updateSignState(state)
                }, {})
        )

    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}