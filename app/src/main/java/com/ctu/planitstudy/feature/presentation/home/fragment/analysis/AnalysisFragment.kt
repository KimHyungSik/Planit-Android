package com.ctu.planitstudy.feature.presentation.home.fragment.analysis

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentAnalysisBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.view_pager.AnalysisViewPager
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class AnalysisFragment : BaseFragment<FragmentAnalysisBinding, AnalysisViewModel>() {

    val TAG = "AnalysisFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentAnalysisBinding
        get() = FragmentAnalysisBinding::inflate

    override val viewModel: AnalysisViewModel by viewModels()

    override fun setInit() {
        super.setInit()
        binding.analysisFragmentViewPager.apply {
            adapter = AnalysisViewPager(requireActivity())
            currentItem = 0
        }
    }
}
