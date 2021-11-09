package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentPlannerDDayBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.home.HomeViewModel

class PlannerDdayFragment : BaseFragment<FragmentPlannerDDayBinding>() {

    val TAG = "DdayFragment - 로그"
    
    override val bindingInflater: (LayoutInflater) -> FragmentPlannerDDayBinding
        get() = FragmentPlannerDDayBinding::inflate

    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun setInit() {
        super.setInit()
        Log.d(TAG, "setInit: $homeViewModel")
        Log.d(TAG, "setInit: ${homeViewModel.homeState.value!!.dDayList}")

    }
}