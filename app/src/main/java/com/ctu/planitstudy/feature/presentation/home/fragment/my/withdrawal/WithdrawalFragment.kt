package com.ctu.planitstudy.feature.presentation.home.fragment.my.withdrawal

import android.os.Bundle
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
import com.ctu.planitstudy.feature.presentation.common.action_bar.ToolBarHelper
import com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail.TermsOfServiceDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalFragment() : BaseFragment<FragmentWithdrawalBinding, WithdrawalViewModel>() {

    override val bindingInflater: (LayoutInflater) -> FragmentWithdrawalBinding
        get() = FragmentWithdrawalBinding::inflate
    override val viewModel by activityViewModels<WithdrawalViewModel>()

    companion object{
        const val TITLE = "탈퇴하기"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showToolbar()

        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewModel.getReward()

        return binding.root
    }

    private fun showToolbar() {
        (activity as AppCompatActivity)?.let { act ->
            ToolBarHelper.showToolbarWithBackButton(
                act,
                TITLE,
                buttonListener = { findNavController().popBackStack() }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}