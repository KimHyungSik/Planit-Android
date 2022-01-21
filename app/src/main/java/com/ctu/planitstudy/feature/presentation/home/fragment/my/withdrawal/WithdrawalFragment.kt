package com.ctu.planitstudy.feature.presentation.home.fragment.my.withdrawal

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.databinding.FragmentWithdrawalBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.common.action_bar.ToolBarHelper
import com.ctu.planitstudy.feature.presentation.dialogs.DialogListener
import com.ctu.planitstudy.feature.presentation.dialogs.DialogText
import com.ctu.planitstudy.feature.presentation.dialogs.SingleTitleDoubleBtnDialog
import com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail.TermsOfServiceDetailFragment
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalFragment() : BaseFragment<FragmentWithdrawalBinding, WithdrawalViewModel>(),
    DialogListener {

    override val bindingInflater: (LayoutInflater) -> FragmentWithdrawalBinding
        get() = FragmentWithdrawalBinding::inflate
    override val viewModel by activityViewModels<WithdrawalViewModel>()

    companion object {
        const val TITLE = "탈퇴하기"
        val TAG = "WithdrawalFragment - 로그"
    }

    private val dialog = SingleTitleDoubleBtnDialog()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        showToolbar()

        binding.vm = viewModel
        binding.act = this
        binding.lifecycleOwner = this

        viewModel.getReward()

        viewModel.deleteState.observe(viewLifecycleOwner) {
            Log.d(TAG, "onCreateView: $it")
            if (it) {
                CashStudyApp.prefs.refreshToken = ""
                CashStudyApp.prefs.accessToken = ""
                moveIntentAffinity(Screens.LoginScreenSh.activity)
            }
        }

        return binding.root
    }

    private fun showToolbar() {
        (activity as AppCompatActivity).let { act ->
            ToolBarHelper.showToolbarWithBackButton(
                act,
                TITLE,
                buttonListener = { findNavController().popBackStack() }
            )
        }
    }

    fun showWithdrawalDialog() {
        val arg = Bundle()
        arg.putString(DialogText.TITLE, getString(R.string.withdrawal_dialog))
        arg.putString(DialogText.RIGHT_BTN_TEXT, "예")
        arg.putString(DialogText.LEFT_BTN_TEXT, "아니요")
        showDialogFragment(arg, dialog)
    }


    override fun onLeftBtn() {
    }

    override fun onRightBtn() {
        viewModel.deleteUser()
    }


}