package com.ctu.planitstudy.feature.presentation.home.fragment.planner.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.home.fragment.analysis.fragment.AnalysisDailyReportFragment
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.*

class AnalysisViewPager(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf<Fragment>(
        AnalysisDailyReportFragment(),
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}
