package com.ctu.planitstudy.feature.presentation.home.fragment.rewards

import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentRewardsBinding

class RewardsFragment : BaseFragment<FragmentRewardsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentRewardsBinding
        get() = FragmentRewardsBinding::inflate
}
