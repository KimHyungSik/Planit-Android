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
import com.ctu.planitstudy.feature.data.data_source.googleadomb.GoogleAdType
import com.ctu.planitstudy.feature.data.data_source.googleadomb.GoogleAdmob
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.presentation.dialogs.ReadyDialog
import com.ctu.planitstudy.feature.presentation.dialogs.SingleTitleCheckDialog
import com.ctu.planitstudy.feature.presentation.sign_up.view_pager.PlanitFragmentStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.abs

@AndroidEntryPoint
class PlanitPassScreen :
    BaseBindingActivity<ActivityPlanitPassScreenBinding, PlanitPassViewModel>()
{

    override val bindingInflater: (LayoutInflater) -> ActivityPlanitPassScreenBinding
        get() = ActivityPlanitPassScreenBinding::inflate

    override val viewModel: PlanitPassViewModel by viewModels()

    private val plaintFragmentStateAdapter = PlanitFragmentStateAdapter(this@PlanitPassScreen)
    var passTitle = ""
    var passEarendStars = ""

    private lateinit var googleAdmob: GoogleAdmob

    val TAG = "PlanitPassScreen - 로그"

    override fun setup() {
        binding.activity = this
        binding.viewmodel = viewModel
        viewModel.rewardDto = intent.getParcelableExtra<RewardDto>("reward") ?: RewardDto(0, 0, 0)

        googleAdmob = GoogleAdmob.Builder().googleAdType(GoogleAdType.Rewarded).build(this)

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

        viewModel.planetPassList.observe(
            this,
            {
                updatePassView()
            }
        )

        viewModel.newPoint.observe(
            this,
            {
                val arg = Bundle()
                arg.putString("title", "${it}별를 획득하였습니다")
                showDialogFragment(arg, SingleTitleCheckDialog())
            }
        )

        googleLoad()
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

    private fun googleLoad() {
        viewModel.loadingShow()
        googleAdmob.InterstitialAdLoad(
            onAdLoadedFun = {
                Log.d(TAG, "googleLoad: ")
                viewModel.loadingDismiss()
                googleAdmob.InterstitialAdCallback()
            },
            onFailedLoad = {
                viewModel.loadingDismiss()
            }
        )
    }

    fun convertPassToPoint() {

        if (viewModel.rewardDto.planetPass == 0) {
            val arg = Bundle()
            arg.putString("title", "보유중인 플래닛 패스가 없습니다.")
            showDialogFragment(arg, SingleTitleCheckDialog())
        } else {
            if (googleAdmob.getInterstitialAd()) {
                googleAdmob.InterstitialAdShow(
                    this,
                    onShowed = {
                        googleLoad()
                        convertPoint()
                    },
                    onFailedLoad = { convertPoint() }
                )
            } else {
                convertPoint()
            }
        }
    }

    private fun convertPoint() {
        viewModel.convertPassToPoint(viewModel.planetPassList.value!!.PlanetPassList[binding.planitPassPlanitViewPager.currentItem].id)
    }

    override fun backScreen() {
        val intent = Intent()
        intent.putExtra("reward", viewModel.rewardDto)
        setResult(0, intent)
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backScreen()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun showReadyDialog() {
        ReadyDialog().show(
            supportFragmentManager, "ReadyDialog"
        )
    }
}
