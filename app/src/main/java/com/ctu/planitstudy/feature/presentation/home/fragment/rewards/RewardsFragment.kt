package com.ctu.planitstudy.feature.presentation.home.fragment.rewards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.setColor
import com.ctu.planitstudy.databinding.FragmentRewardsBinding
import com.ctu.planitstudy.feature.data.data_source.googleadomb.GoogleAdType
import com.ctu.planitstudy.feature.data.data_source.googleadomb.GoogleAdmob
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.dialogs.SingleTitleCheckDialog
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RewardsFragment : BaseFragment<FragmentRewardsBinding, RewardViewModel>() {

    val TAG = "RewardsFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentRewardsBinding
        get() = FragmentRewardsBinding::inflate
    override val viewModel: RewardViewModel by activityViewModels<RewardViewModel>()

    private var isAnimated = false
    private lateinit var googleAdmob: GoogleAdmob

    val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() // ◀ StartActivityForResult 처리를 담당
    ) { activityResult ->
        viewModel.updateRewardDto(
            activityResult.data?.getParcelableExtra<RewardDto>("reward") ?: RewardDto(0, 0, 0)
        )
    }

    override fun setInit() {
        super.setInit()

        googleAdmob = GoogleAdmob.Builder().googleAdType(GoogleAdType.FullPage).build(requireContext())

        viewModel.getReward()
        googleLoad()

        with(binding) {
            activity = this@RewardsFragment
            viewmodel = viewModel

            with(rewardsFragmentMainRewardLottie) {
                repeatCount = LottieDrawable.INFINITE
                playAnimation()
            }
        }

        with(viewModel) {
            rewardDto.observe(
                this@RewardsFragment,
                {
                    binding.invalidateAll()

                    if (it.star >= 50)
                        binding.rewardsFragmentMainRewardLottie.playAnimation()

                    binding.rewardsFragmentPlanitPassCountText.background =
                        ContextCompat.getDrawable(
                            CashStudyApp.instance,
                            if (it.planetPass == 0) R.drawable.gray_circle_background
                            else R.drawable.subcolor_circle_background
                        )
                    binding.rewardsFragmentStarCountText.setTextColor(
                        setColor(
                            if (it.star < 50) R.color.guide_text
                            else R.color.sub_color
                        )
                    )
                    binding.rewardsFragmentStarText.setTextColor(
                        setColor(
                            if (it.star < 50) R.color.guide_text
                            else R.color.sub_color
                        )
                    )
                }
            )
            newPoint.observe(
                this@RewardsFragment,
                {
                    if (it != null) {
                        val arg = Bundle()
                        arg.putString("title", "${it}포인트를 획득하였습니다")
                        showDialogFragment(arg, SingleTitleCheckDialog())
                    }
                }
            )
        }
    }

    fun touchRewardStar() {
        if (!isAnimated && viewModel.rewardDto.value!!.star >= 50) {
            if (googleAdmob.getInterstitialAd()) {
                googleAdmob.InterstitialAdShow(
                    activity = requireActivity(),
                    onFailedLoad = {
                        googleLoad()
                        getPoint()
                    }
                )
            } else {
                googleLoad()
                getPoint()
            }
        }
    }

    private fun getPoint() {
        isAnimated = true
        binding.rewardsFragmentMainRewardLottie.setAnimation(R.raw.reward_star_lottie)
        binding.rewardsFragmentMainRewardLottie.playAnimation()

        CoroutineScope(Dispatchers.Main + mainJob).launch {
            delay(binding.rewardsFragmentMainRewardLottie.duration)
            viewModel.convertStarToPoint()
            isAnimated = false
            binding.rewardsFragmentMainRewardLottie.setAnimation(R.raw.reward_ready_lottie)
            if (viewModel.rewardDto.value!!.star >= 50)
                binding.rewardsFragmentMainRewardLottie.playAnimation()
        }
    }

    fun clickPanitPass() {
        if (viewModel.rewardDto.value!!.planetPass == 0) {
            val arg = Bundle()
            val dialog = SingleTitleCheckDialog()
            arg.putString("title", getString(R.string.empty_planet_pass_ticket))
            dialog.arguments = arg
            dialog.show(parentFragmentManager, "titleDialog")
        } else {
            val intent = Intent(context, Screens.PlanitPassScreenSh.activity)
            intent.putExtra("reward", viewModel.rewardDto.value)
            requestActivity.launch(intent)
        }
    }

    private fun googleLoad() {
        viewModel.loadingShow()
        googleAdmob.InterstitialAdLoad(
            onAdLoadedFun = {
                googleAdmob.InterstitialAdCallback(
                    onAdDismissed = {
                        googleLoad()
                        getPoint()
                    }
                )
                viewModel.loadingDismiss()
            },
            onFailedLoad = {
                viewModel.loadingDismiss()
            }
        )
    }

    fun navRewardShop() {
        findNavController().navigate(R.id.action_rewardsFragment_to_rewardShopFragment)
    }
}
