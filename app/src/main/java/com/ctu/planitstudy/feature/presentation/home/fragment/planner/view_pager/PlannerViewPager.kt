package com.ctu.planitstudy.feature.presentation.home.fragment.planner.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.PlannerDdayFragment
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.PlannerPlannerFragment

class PlannerViewPager(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf<Fragment>(
        PlannerPlannerFragment(),
        PlannerDdayFragment(),
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}
