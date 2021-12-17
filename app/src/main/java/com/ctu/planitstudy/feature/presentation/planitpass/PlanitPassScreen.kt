package com.ctu.planitstudy.feature.presentation.planitpass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityPlanitPassScreenBinding
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.presentation.dialogs.SingleTitleCheckDialog
import com.ctu.planitstudy.feature.presentation.sign_up.view_pager.PlanitFragmentStateAdapter
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.abs


@AndroidEntryPoint
class PlanitPassScreen :
    BaseBindingActivity<ActivityPlanitPassScreenBinding, PlanitPassViewModel>() {

    override val bindingInflater: (LayoutInflater) -> ActivityPlanitPassScreenBinding
        get() = ActivityPlanitPassScreenBinding::inflate

    override val viewModel: PlanitPassViewModel by viewModels()

    private val plaintFragmentStateAdapter = PlanitFragmentStateAdapter(this@PlanitPassScreen)
    var passTitle = ""
    var passEarendStars = ""
    val TAG = "PlanitPassScreen - 로그"

    private var mInterstitialAd: InterstitialAd? = null

    override fun setup() {
        binding.activity = this
        binding.viewmodel = viewModel

        viewModel.rewardDto = intent.getParcelableExtra<RewardDto>("reward") ?: RewardDto(0, 0, 0)
        with(binding.planitPassPlanitViewPager) {

            apply {
                adapter = plaintFragmentStateAdapter
                currentItem = 0
            }

            offscreenPageLimit = 2
            clipToPadding = false

            setPageTransformer { page, position ->
                page.scaleY = 1 - (0.25f * abs(position))
            }

            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        updatePassView()
                    }
                }
            )
        }
        binding.planitPassPlanitViewPagerIndicator.setViewPager2(binding.planitPassPlanitViewPager)

        viewModel.planetPassList.observe(this, {
            updatePassView()
        })

        viewModel.newPoint.observe(this, {
            val arg = Bundle()
            arg.putString("title", "${it}별를 획득하였습니다")
            showDialogFragment(arg, SingleTitleCheckDialog())
        })
        loadAds()
    }

    fun convertPassToPoint() {

        if (viewModel.rewardDto.planetPass == 0) {
            val arg = Bundle()
            arg.putString("title", "보유중인 플래닛 패스가 없습니다.")
            showDialogFragment(arg, SingleTitleCheckDialog())
        } else {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
                loadAds()
            }else{
                viewModel.convertPassToPoint(viewModel.planetPassList.value!!.PlanetPassList[binding.planitPassPlanitViewPager.currentItem].id)
            }
        }
    }

    fun nextPass() {
        with(binding.planitPassPlanitViewPager) {
            if (currentItem < plaintFragmentStateAdapter.fragmentList.size) {
                currentItem += 1
                updatePassView()
            }
        }
    }

    fun previousPass() {
        with(binding.planitPassPlanitViewPager) {
            if (currentItem > 0) {
                currentItem -= 1
                updatePassView()
            }
        }
    }

    private fun updatePassView() {
        with(binding.planitPassPlanitViewPager) {
            viewModel.planetPassList.value?.let {
                passTitle =
                    it.PlanetPassList[currentItem].name
                passEarendStars =
                    it.PlanetPassList[currentItem].description
            } ?: {
                passTitle = ""
                passEarendStars = ""
            }
        }
        binding.invalidateAll()
    }

    override fun backScreen() {
        val intent = Intent()
        intent.putExtra("reward", viewModel.rewardDto)
        setResult(0, intent)
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            val intent = Intent()
            intent.putExtra("reward", viewModel.rewardDto)
            setResult(0, intent)
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun loadAds(){
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    callBackAds()
                }
            })
    }

    private fun callBackAds(){
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdImpression() {
                super.onAdImpression()
                Log.d(TAG, "onAdImpression: ")
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
                loadAds()
                viewModel.convertPassToPoint(viewModel.planetPassList.value!!.PlanetPassList[binding.planitPassPlanitViewPager.currentItem].id)
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
}
