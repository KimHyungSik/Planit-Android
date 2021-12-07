package com.ctu.planitstudy.feature.presentation.planitpass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityPlanitPassScreenBinding
import com.ctu.planitstudy.feature.presentation.sign_up.view_pager.PlanitFragmentStateAdapter
import java.lang.Math.abs

class PlanitPassScreen : BaseBindingActivity<ActivityPlanitPassScreenBinding, PlanitPassViewModel>() {
    override val bindingInflater: (LayoutInflater) -> ActivityPlanitPassScreenBinding
        get() = ActivityPlanitPassScreenBinding::inflate
    override val viewModel: PlanitPassViewModel by viewModels()

    override fun setup() {

        with(binding.planitPassPlanitViewPager) {
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.viewpager_current_item_horizontal_margin)
            val pagerWidth = resources.getDimensionPixelOffset(R.dimen.viewpager_next_item_visible)
            val offsetPx = pageMarginPx + pagerWidth

            apply {
                adapter = PlanitFragmentStateAdapter(this@PlanitPassScreen)
                currentItem = 0
            }
            offscreenPageLimit = 2
            setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
                page.scaleY = 1 - (0.25f * abs(position))
            }
        }
        binding.planitPassPlanitViewPagerIndicator.setViewPager2(binding.planitPassPlanitViewPager)
    }
}