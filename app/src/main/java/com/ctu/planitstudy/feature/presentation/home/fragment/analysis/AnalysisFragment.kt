package com.ctu.planitstudy.feature.presentation.home.fragment.analysis

import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentAnalysisBinding

class AnalysisFragment : BaseFragment<FragmentAnalysisBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentAnalysisBinding
        get() = FragmentAnalysisBinding::inflate
}
