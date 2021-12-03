package com.ctu.planitstudy.feature.presentation.home.fragment.rewards

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentRewardsBinding

class RewardsFragment : BaseFragment<FragmentRewardsBinding, RewardViewModel>() {

    override val bindingInflater: (LayoutInflater) -> FragmentRewardsBinding
        get() = FragmentRewardsBinding::inflate
    override val viewModel: RewardViewModel by activityViewModels<RewardViewModel>()
}
