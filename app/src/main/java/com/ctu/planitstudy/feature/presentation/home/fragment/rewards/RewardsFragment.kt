package com.ctu.planitstudy.feature.presentation.home.fragment.rewards

import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentRewardsBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.util.Screens

class RewardsFragment : BaseFragment<FragmentRewardsBinding, RewardViewModel>() {

    override val bindingInflater: (LayoutInflater) -> FragmentRewardsBinding
        get() = FragmentRewardsBinding::inflate
    override val viewModel: RewardViewModel by activityViewModels<RewardViewModel>()
    private val rotateAnimation = AnimationUtils.loadAnimation(
        CashStudyApp.instance,
        R.anim.rotate_main_reward
    )

    override fun setInit() {
        super.setInit()
        binding.rewardsFragmentMainRewardImg.startAnimation(
            rotateAnimation
        )

        binding.rewardsFragmentPlanitPassColumn.setOnClickListener {
            moveIntent(Screens.PlanitPassScreenSh.activity)
        }
    }
}
