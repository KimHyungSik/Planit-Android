package com.ctu.planitstudy.feature.presentation.home.fragment.my.withdrawal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ctu.planitstudy.R
import com.ctu.planitstudy.databinding.FragmentWithdrawalBinding
import com.ctu.planitstudy.feature.presentation.common.action_bar.ToolBarHelper
import com.ctu.planitstudy.feature.presentation.home.fragment.my.termsofservicedetail.TermsOfServiceDetailFragment

class WithdrawalFragment : Fragment() {

    companion object{
        const val TITLE = "탈퇴하기"
    }

    private var _binding: FragmentWithdrawalBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWithdrawalBinding.inflate(inflater, container, false)
        showToolbar()
        return binding.root
    }

    fun showToolbar() {
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
        _binding = null
    }
}