package com.ctu.planitstudy.feature.presentation.home.fragment.rewards.rewardshop.list

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentRewardShopListBinding
import com.ctu.planitstudy.feature.presentation.common.action_bar.ToolBarHelper
import com.ctu.planitstudy.feature.presentation.home.HomeScreen

class RewardShopFragment : BaseFragment<FragmentRewardShopListBinding, RewardViewModel>() {
    override val bindingInflater: (LayoutInflater) -> FragmentRewardShopListBinding
        get() = FragmentRewardShopListBinding::inflate
    override val viewModel: RewardViewModel by activityViewModels<RewardViewModel>()

    companion object {
        val TITLE = "리워드 샵"
    }

    override fun setInit() {
        super.setInit()
        bottomNavController(false)
        showToolbar()
    }

    fun showToolbar() {
        (activity as AppCompatActivity).let { act ->
            ToolBarHelper.showToolbarWithBackButton(
                act,
                TITLE,
                buttonListener = { findNavController().popBackStack() }
            )
        }
    }

    fun bottomNavController(visible: Boolean) {
        activity?.let { act ->
            if (act is HomeScreen) {
                act.visibleBottomNav(visible)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity)?.let { act ->
            ToolBarHelper.hideToolbar(act)
        }
        bottomNavController(true)
    }
}
