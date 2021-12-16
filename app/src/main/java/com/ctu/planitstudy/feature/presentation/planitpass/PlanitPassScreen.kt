package com.ctu.planitstudy.feature.presentation.planitpass

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityPlanitPassScreenBinding
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.presentation.dialogs.ReadyDialog
import com.ctu.planitstudy.feature.presentation.dialogs.SingleTitleCheckDialog
import com.ctu.planitstudy.feature.presentation.home.fragment.rewards.RewardViewModel
import com.ctu.planitstudy.feature.presentation.sign_up.view_pager.PlanitFragmentStateAdapter
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
    }

    fun convertPassToPoint() {
        if (viewModel.rewardDto.planetPass == 0) {
            val arg = Bundle()
            arg.putString("title", "보유중인 플래닛 패스가 없습니다.")
            showDialogFragment(arg, SingleTitleCheckDialog())
        } else {
            viewModel.convertPassToPoint(viewModel.planetPassList.value!!.PlanetPassList[binding.planitPassPlanitViewPager.currentItem].id)
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

    fun showReadyDialog() {
        ReadyDialog().show(
            supportFragmentManager, "ReadyDialog"
        )
    }
}
