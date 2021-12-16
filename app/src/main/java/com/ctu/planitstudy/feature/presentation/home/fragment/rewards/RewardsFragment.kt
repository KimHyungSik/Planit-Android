package com.ctu.planitstudy.feature.presentation.home.fragment.rewards

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.LottieDrawable
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.setColor
import com.ctu.planitstudy.databinding.FragmentRewardsBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.dialogs.ReadyDialog
import com.ctu.planitstudy.feature.presentation.dialogs.SingleTitleCheckDialog
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class RewardsFragment : BaseFragment<FragmentRewardsBinding, RewardViewModel>() {

    val TAG = "RewardsFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentRewardsBinding
        get() = FragmentRewardsBinding::inflate
    override val viewModel: RewardViewModel by activityViewModels<RewardViewModel>()

    private var isAnimated = false

    private val lottieMaxFrame = 214
    override fun setInit() {
        super.setInit()

        viewModel.getReward()

        with(binding) {
            activity = this@RewardsFragment
            viewmodel = viewModel

            rewardsFragmentPlanitPassColumn.setOnClickListener {
                if(viewModel.rewardDto.value!!.planetPass == 0){
                    val arg = Bundle()
                    val dialog = SingleTitleCheckDialog()
                    arg.putString("title", getString(R.string.empty_planet_pass_ticket))
                    dialog.arguments = arg
                    dialog.show(parentFragmentManager, "titleDialog")
                }else {
                    moveIntent(Screens.PlanitPassScreenSh.activity)
                }
            }

            with(rewardsFragmentMainRewardLottie) {
                repeatCount = LottieDrawable.INFINITE
                playAnimation()
            }
        }
        with(viewModel) {
            rewardDto.observe(this@RewardsFragment, {
                binding.invalidateAll()

                binding.rewardsFragmentPlanitPassCountText.background = ContextCompat.getDrawable(
                    CashStudyApp.instance,
                    if (it.planetPass == 0) R.drawable.gray_circle_background
                    else R.drawable.subcolor_circle_background
                )
                binding.rewardsFragmentStarCountText.setTextColor(
                    setColor(
                        if (it.star == 0) R.color.guide_text
                        else R.color.sub_color
                    )
                )
                binding.rewardsFragmentStarText.setTextColor(
                    setColor(
                        if (it.star == 0) R.color.guide_text
                        else R.color.sub_color
                    )
                )
            })
        }
    }

    fun touchRewardStar() {
        if (!isAnimated && viewModel.rewardDto.value!!.star >= 50) {
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
