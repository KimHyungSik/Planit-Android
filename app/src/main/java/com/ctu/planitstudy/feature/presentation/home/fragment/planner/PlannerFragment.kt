package com.ctu.planitstudy.feature.presentation.home.fragment.planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentAnalysisBinding
import com.ctu.planitstudy.databinding.FragmentPlannerBinding
import com.ctu.planitstudy.databinding.FragmentPlannerPlannerBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.home.HomeViewModel
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.PlannerDdayFragment
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.PlannerPlannerFragment
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.recycler.InDdayListRecycler
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import io.reactivex.disposables.CompositeDisposable

class PlannerFragment : BaseFragment<FragmentPlannerBinding>(){

    val TAG = "PlannerFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentPlannerBinding
        get() = FragmentPlannerBinding::inflate

    private val disposables = CompositeDisposable()
    
    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun setUpViews() {
        super.setUpViews()
        binding.plannerRadioButton.check(R.id.planner_radio_planner_button)
        binding.plannerRadioPlannerButton.setTextColor(resources.getColor(R.color.text_color))
        childFragmentManager.beginTransaction()
            .add(R.id.planner_fragment_view, PlannerPlannerFragment())
            .commit()
        disposables.add(
            RxRadioGroup.checkedChanges(binding.plannerRadioButton)
                .subscribe({
                    when (it) {
                        R.id.planner_radio_planner_button -> {
                            binding.plannerRadioDDayButton.setTextColor(resources.getColor(R.color.enabled_text_color))
                            binding.plannerRadioPlannerButton.setTextColor(resources.getColor(R.color.text_color))
                            childFragmentManager.beginTransaction()
                                .replace(R.id.planner_fragment_view, PlannerPlannerFragment())
                                .commit()
                        }
                        R.id.planner_radio_d_day_button -> {
                            binding.plannerRadioPlannerButton.setTextColor(resources.getColor(R.color.enabled_text_color))
                            binding.plannerRadioDDayButton.setTextColor(resources.getColor(R.color.text_color))
                            childFragmentManager.beginTransaction()
                                .replace(R.id.planner_fragment_view, PlannerDdayFragment())
                                .commit()
                        }
                    }
                }, {}, {})
        )
    }

    override fun setInit() {
        super.setInit()
        Log.d(TAG, "setInit: $homeViewModel")
        Log.d(TAG, "setInit: ${homeViewModel.homeState.value!!.dDayList}")

    }


    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

}