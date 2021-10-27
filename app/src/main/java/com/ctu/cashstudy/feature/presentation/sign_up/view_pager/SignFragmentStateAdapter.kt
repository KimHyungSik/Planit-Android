package com.ctu.cashstudy.feature.presentation.sign_up.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ctu.cashstudy.feature.presentation.sign_up.fragment.*

class SignFragmentStateAdapter(fragmentActivity : FragmentActivity)
    : FragmentStateAdapter(fragmentActivity)
{

    val fragmentList = listOf<Fragment>(
        SignUserNameFragment(),
        SignUserGenderFragment(),
        SignUserDateOfBirthFragment(),
        SignUserCategory(),
    )

    override fun getItemCount(): Int  = fragmentList.size

    override fun createFragment(position: Int): Fragment  = fragmentList[position]
}