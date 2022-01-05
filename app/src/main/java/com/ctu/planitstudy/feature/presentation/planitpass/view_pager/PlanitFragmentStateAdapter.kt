package com.ctu.planitstudy.feature.presentation.sign_up.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.planitpass.fragment.FragmentChallengePlanit
import com.ctu.planitstudy.feature.presentation.planitpass.fragment.FragmentFunPlanit
import com.ctu.planitstudy.feature.presentation.planitpass.fragment.FragmentSafePlanit

class PlanitFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf<Fragment>(
        FragmentSafePlanit(),
        FragmentFunPlanit(),
        FragmentChallengePlanit()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}
