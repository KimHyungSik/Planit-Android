package com.ctu.planitstudy.feature.presentation.home.fragment.analysis

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.setColor
import com.ctu.planitstudy.databinding.FragmentAnalysisBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.view_pager.AnalysisViewPager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisFragment : BaseFragment<FragmentAnalysisBinding, AnalysisViewModel>() {

    val TAG = "AnalysisFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentAnalysisBinding
        get() = FragmentAnalysisBinding::inflate

    override val viewModel: AnalysisViewModel by activityViewModels<AnalysisViewModel>()

    override fun setInit() {
        super.setInit()
        binding.activity = this
        binding.analysisFragmentViewPager.apply {
            adapter = AnalysisViewPager(requireActivity())
            currentItem = 0
        }
        binding.analysisFragmentViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == 0) {
                        toggleButtonChanged(false)
                    } else {
                        toggleButtonChanged(true)
                    }
                }
            }
        )
    }

    fun toggleButtonChanged(checked: Boolean) {
        with(binding) {
            analysisFragmentDailyToggle.setCardBackgroundColor(
                setColor(
                    if (checked) { R.color.module_color } else { R.color.sub_color }
                )
            )
            analysisFragmentAnalysisToggle.setCardBackgroundColor(
                setColor(
                    if (checked) {
                        R.color.sub_color
                    } else {
                        R.color.module_color
                    }
                )
            )
            if (!checked && analysisFragmentViewPager.currentItem != 0) {
                analysisFragmentViewPager.currentItem = 0
            }
            if (checked && analysisFragmentViewPager.currentItem != 1) {
                analysisFragmentViewPager.currentItem = 1
            }
        }
    }
}
