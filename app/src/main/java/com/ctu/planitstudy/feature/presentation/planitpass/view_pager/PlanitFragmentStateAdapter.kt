package com.ctu.planitstudy.feature.presentation.sign_up.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.planitpass.fragment.FragmentChallengePlanit
import com.ctu.planitstudy.feature.presentation.planitpass.fragment.FragmentFunPlanit
import com.ctu.planitstudy.feature.presentation.planitpass.fragment.FragmentSafePlanit
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.*

class PlanitFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf<Fragment>(
        FragmentSafePlanit(),
        FragmentFunPlanit(),
        FragmentChallengePlanit()
    )

    val planitPassTitle = listOf<String>(
        "안전 제일 플래닛",
        "재미 유발 플래닛",
        "도전 가득 플래닛"
    )

    val planitPassEarendStars = listOf<String>(
        "10별 획득",
        "5~15까지 별 랜덤 획득",
        "1~20까지 별 랜덤 획득"
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}
