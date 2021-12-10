package com.ctu.planitstudy.feature.presentation.planitpass

import android.graphics.Point
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityPlanitPassScreenBinding
import com.ctu.planitstudy.feature.presentation.sign_up.view_pager.PlanitFragmentStateAdapter
import java.lang.Math.abs

class PlanitPassScreen :
    BaseBindingActivity<ActivityPlanitPassScreenBinding, PlanitPassViewModel>() {
    override val bindingInflater: (LayoutInflater) -> ActivityPlanitPassScreenBinding
        get() = ActivityPlanitPassScreenBinding::inflate
    override val viewModel: PlanitPassViewModel by viewModels()
    private val plaintFragmentStateAdapter = PlanitFragmentStateAdapter(this@PlanitPassScreen)
    var passTitle = plaintFragmentStateAdapter.planitPassTitle.first()
    var passEarendStars = plaintFragmentStateAdapter.planitPassEarendStars.first()

    val TAG = "PlanitPassScreen - 로그"
    
    override fun setup() {
        binding.activity = this
        binding.viewmodel = viewModel

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
            passTitle =
                plaintFragmentStateAdapter.planitPassTitle[currentItem]
            passEarendStars =
                plaintFragmentStateAdapter.planitPassEarendStars[currentItem]
        }
        binding.invalidateAll()
    }
}
