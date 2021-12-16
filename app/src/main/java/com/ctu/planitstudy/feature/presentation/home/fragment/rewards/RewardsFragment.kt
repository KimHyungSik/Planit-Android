package com.ctu.planitstudy.feature.presentation.home.fragment.rewards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class RewardsFragment : BaseFragment<FragmentRewardsBinding, RewardViewModel>() {

    val TAG = "RewardsFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentRewardsBinding
        get() = FragmentRewardsBinding::inflate
    override val viewModel: RewardViewModel by activityViewModels<RewardViewModel>()

    private var isAnimated = false
    private var mInterstitialAd: InterstitialAd? = null

    override fun setInit() {
        super.setInit()
        viewModel.getReward()
        Log.d(TAG, "setInit: $viewModel")

        with(binding) {
            activity = this@RewardsFragment
            viewmodel = viewModel

            rewardsFragmentPlanitPassColumn.setOnClickListener {
                if(viewModel.rewardDto.value!!.planetPass == 0){
                    val arg = Bundle()
                    arg.putString("title", getString(R.string.empty_planet_pass_ticket))
                    showDialogFragment(arg, SingleTitleCheckDialog())
                }else {
                    val intent = Intent(context, Screens.PlanitPassScreenSh.activity)
                    intent.putExtra("reward", viewModel.rewardDto.value)
                    moveIntent(intent)
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

            newPoint.observe(this@RewardsFragment, {
                val arg = Bundle()
                arg.putString("title", "${it}포인트를 획득하였습니다")
                showDialogFragment(arg, SingleTitleCheckDialog())
            })
        }
    }

    private fun loadAds(){
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun callBackAds(){
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
                binding.rewardsFragmentMainRewardLottie.setAnimation(R.raw.reward_star_lottie)
                binding.rewardsFragmentMainRewardLottie.playAnimation()

                CoroutineScope(Dispatchers.Main).launch {
                    delay(binding.rewardsFragmentMainRewardLottie.duration)
                    isAnimated = false
                    binding.rewardsFragmentMainRewardLottie.setAnimation(R.raw.reward_ready_lottie)
                    binding.rewardsFragmentMainRewardLottie.playAnimation()
                }
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }
    }

    fun touchRewardStar() {
        if (!isAnimated && viewModel.rewardDto.value!!.star >= 50) {
//            if (mInterstitialAd != null) {
//                mInterstitialAd?.show(activity)
//                isAnimated = true
//            } else {
//                Log.d("TAG", "The interstitial ad wasn't ready yet.")
//            }
            binding.rewardsFragmentMainRewardLottie.setAnimation(R.raw.reward_star_lottie)
            binding.rewardsFragmentMainRewardLottie.playAnimation()
            isAnimated = true
            CoroutineScope(Dispatchers.Main).launch {
                delay(binding.rewardsFragmentMainRewardLottie.duration)
                isAnimated = false
                binding.rewardsFragmentMainRewardLottie.setAnimation(R.raw.reward_ready_lottie)
                binding.rewardsFragmentMainRewardLottie.playAnimation()
                viewModel.convertStarToPoint()
            }
        }
//        loadAds()

    }

    fun showReadyDialog() {
        ReadyDialog().show(
            parentFragmentManager, "ReadyDialog"
        )
    }
}
