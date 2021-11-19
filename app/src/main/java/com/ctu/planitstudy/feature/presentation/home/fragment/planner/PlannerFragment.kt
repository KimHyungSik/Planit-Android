package com.ctu.planitstudy.feature.presentation.home.fragment.planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentAnalysisBinding
import com.ctu.planitstudy.databinding.FragmentPlannerBinding
import com.ctu.planitstudy.databinding.FragmentPlannerPlannerBinding
import com.ctu.planitstudy.feature.presentation.dday.DdayScreen
import com.ctu.planitstudy.feature.presentation.home.fragment.home.HomeViewModel
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.PlannerDdayFragment
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.PlannerPlannerFragment
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.recycler.InDdayListRecycler
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.view_pager.PlannerViewPager
import com.ctu.planitstudy.feature.presentation.sign_up.view_pager.SignFragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.util.Screens
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
        binding.plannerViewPagerFragmentView.apply {
            adapter = PlannerViewPager(requireActivity())
            currentItem = 0
        }
        binding.plannerViewPagerFragmentView.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d(TAG, "onPageSelected: $position")
                    if (position == 0)
                        binding.plannerRadioButton.check(R.id.planner_radio_planner_button)
                    else
                        binding.plannerRadioButton.check(R.id.planner_radio_d_day_button)
                }
            }
        )
        disposables.add(
            RxRadioGroup.checkedChanges(binding.plannerRadioButton)
                .subscribe({
                    when (it) {
                        R.id.planner_radio_planner_button -> {
                            binding.plannerRadioDDayButton.setTextColor(resources.getColor(R.color.enabled_text_color))
                            binding.plannerRadioPlannerButton.setTextColor(resources.getColor(R.color.text_color))
                            binding.studyAddedBtn.visibility = View.VISIBLE
                            binding.dDayAddedBtn.visibility = View.GONE
                            binding.plannerViewPagerFragmentView.currentItem = 0
                        }
                        R.id.planner_radio_d_day_button -> {
                            binding.plannerRadioPlannerButton.setTextColor(resources.getColor(R.color.enabled_text_color))
                            binding.plannerRadioDDayButton.setTextColor(resources.getColor(R.color.text_color))
                            binding.studyAddedBtn.visibility = View.GONE
                            binding.dDayAddedBtn.visibility = View.VISIBLE
                            binding.plannerViewPagerFragmentView.currentItem = 1
                        }
                    }
                }, {}, {})
        )

        binding.dDayAddedBtn.setOnClickListener {
            moveIntent(Screens.DdayScreenSh.activity)
        }

        binding.studyAddedBtn.setOnClickListener {
            moveIntent(Screens.StudyScreenSh.activity)
        }
    }

    override fun setInit() {
        super.setInit()

    }


    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

}