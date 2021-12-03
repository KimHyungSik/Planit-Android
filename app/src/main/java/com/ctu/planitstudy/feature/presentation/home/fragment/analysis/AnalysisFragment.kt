package com.ctu.planitstudy.feature.presentation.home.fragment.analysis

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentAnalysisBinding

class AnalysisFragment : BaseFragment<FragmentAnalysisBinding, AnalysisViewModel>() {

    override val bindingInflater: (LayoutInflater) -> FragmentAnalysisBinding
        get() = FragmentAnalysisBinding::inflate
    override val viewModel: AnalysisViewModel by viewModels()
}
