package com.ctu.planitstudy.feature.presentation.home.fragment.analysis.fragment

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentAnalysisPageBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.analysis.AnalysisViewModel

class AnalysisAnalysisFragment :
    BaseFragment<FragmentAnalysisPageBinding, AnalysisViewModel>() {

    override val bindingInflater: (LayoutInflater) -> FragmentAnalysisPageBinding
        get() = FragmentAnalysisPageBinding::inflate

    override val viewModel: AnalysisViewModel by activityViewModels<AnalysisViewModel>()
}
