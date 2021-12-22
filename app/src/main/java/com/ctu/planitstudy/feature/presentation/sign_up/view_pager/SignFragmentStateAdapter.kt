package com.ctu.planitstudy.feature.presentation.sign_up.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.SignUpUserReceiverName
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.SignUserCategory
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.SignUserDateOfBirthFragment
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.SignUserGenderFragment
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.SignUserNameFragment

class SignFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf<Fragment>(
        SignUserNameFragment(),
        SignUserGenderFragment(),
        SignUserDateOfBirthFragment(),
        SignUserCategory(),
        SignUpUserReceiverName()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}
