package com.ctu.planitstudy.feature.presentation.home.fragment.planner

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentPlannerBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.home.fragment.home.HomeViewModel
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.view_pager.PlannerViewPager
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import io.reactivex.disposables.CompositeDisposable

class PlannerFragment : BaseFragment<FragmentPlannerBinding>() {

    val TAG = "PlannerFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentPlannerBinding
        get() = FragmentPlannerBinding::inflate

    private val disposables = CompositeDisposable()

    private val homeViewModel by activityViewModels<HomeViewModel>()

    @SuppressLint("ResourceAsColor")
    override fun setUpViews() {
        super.setUpViews()
        with(binding) {
            plannerRadioButton.check(R.id.planner_radio_planner_button)
            plannerRadioPlannerButton.setTextColor(ContextCompat.getColor(CashStudyApp.instance, R.color.text_color))
            plannerViewPagerFragmentView.apply {
                adapter = PlannerViewPager(requireActivity())
                currentItem = 0
            }
            plannerViewPagerFragmentView.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        Log.d(TAG, "onPageSelected: $position")
                        if (position == 0)
                            plannerRadioButton.check(R.id.planner_radio_planner_button)
                        else
                            plannerRadioButton.check(R.id.planner_radio_d_day_button)
                    }
                }
            )
        }
        disposables.add(
            RxRadioGroup.checkedChanges(binding.plannerRadioButton)
                .subscribe({
                    with(binding) {
                        when (it) {
                            R.id.planner_radio_planner_button -> {
                                plannerRadioDDayButton.setTextColor(ContextCompat.getColor(CashStudyApp.instance, R.color.enabled_text_color))
                                plannerRadioPlannerButton.setTextColor(ContextCompat.getColor(CashStudyApp.instance, R.color.text_color))
                                studyAddedBtn.visibility = View.VISIBLE
                                dDayAddedBtn.visibility = View.GONE
                                plannerViewPagerFragmentView.currentItem = 0
                            }
                            R.id.planner_radio_d_day_button -> {
                                plannerRadioPlannerButton.setTextColor(ContextCompat.getColor(CashStudyApp.instance, R.color.enabled_text_color))
                                plannerRadioDDayButton.setTextColor(ContextCompat.getColor(CashStudyApp.instance, R.color.text_color))
                                studyAddedBtn.visibility = View.GONE
                                dDayAddedBtn.visibility = View.VISIBLE
                                plannerViewPagerFragmentView.currentItem = 1
                            }
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
