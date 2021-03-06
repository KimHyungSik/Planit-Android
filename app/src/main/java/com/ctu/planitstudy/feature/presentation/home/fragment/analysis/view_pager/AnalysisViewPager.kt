package com.ctu.planitstudy.feature.presentation.home.fragment.planner.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.home.fragment.analysis.fragment.AnalysisAnalysisFragment
import com.ctu.planitstudy.feature.presentation.home.fragment.analysis.fragment.AnalysisDailyReportFragment

class AnalysisViewPager(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf<Fragment>(
        AnalysisDailyReportFragment(),
        AnalysisAnalysisFragment()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}
