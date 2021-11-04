package com.ctu.planitstudy.feature.presentation.home.fragment.planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentAnalysisBinding
import com.ctu.planitstudy.databinding.FragmentPlannerBinding

class PlannerFragment : BaseFragment<FragmentPlannerBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentPlannerBinding
        get() = FragmentPlannerBinding::inflate
}