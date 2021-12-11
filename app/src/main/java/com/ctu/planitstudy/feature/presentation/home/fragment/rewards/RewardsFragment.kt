package com.ctu.planitstudy.feature.presentation.home.fragment.rewards

import android.animation.ValueAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.LottieDrawable
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentRewardsBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.dialogs.ReadyDialog
import com.ctu.planitstudy.feature.presentation.util.Screens
import kotlinx.coroutines.*

class RewardsFragment : BaseFragment<FragmentRewardsBinding, RewardViewModel>() {

    val TAG = "RewardsFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentRewardsBinding
        get() = FragmentRewardsBinding::inflate
    override val viewModel: RewardViewModel by activityViewModels<RewardViewModel>()

    private var isAnimated = false

    private val lottieMaxFrame = 214
    override fun setInit() {
        super.setInit()

        with(binding) {
            activity = this@RewardsFragment
            viewmodel = viewModel

            rewardsFragmentPlanitPassColumn.setOnClickListener {
                moveIntent(Screens.PlanitPassScreenSh.activity)
            }

            with(rewardsFragmentMainRewardLottie){
                repeatCount = LottieDrawable.INFINITE
                playAnimation()
            }
        }
    }

    fun touchRewardStar() {
        if (!isAnimated) {
            binding.rewardsFragmentMainRewardLottie.setAnimation(R.raw.reward_star_lottie)
            binding.rewardsFragmentMainRewardLottie.playAnimation()

            isAnimated = true
            CoroutineScope(Dispatchers.Main).launch {
                delay(binding.rewardsFragmentMainRewardLottie.duration)
                isAnimated = false
                binding.rewardsFragmentMainRewardLottie.setAnimation(R.raw.reward_ready_lottie)
                binding.rewardsFragmentMainRewardLottie.playAnimation()
            }
        }
    }

    fun showReadyDialog() {
        ReadyDialog().show(
            parentFragmentManager, "ReadyDialog"
        )
    }
}
