package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments

import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentPlannerPlannerBinding

class PlannerPlannerFragment : BaseFragment<FragmentPlannerPlannerBinding>() {
    override val bindingInflater: (LayoutInflater) -> FragmentPlannerPlannerBinding
        get() = FragmentPlannerPlannerBinding::inflate
}