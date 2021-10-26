package com.ctu.cashstudy.feature.presentation.sign_up.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ctu.cashstudy.feature.presentation.sign_up.fragment.SignUserCategory
import com.ctu.cashstudy.feature.presentation.sign_up.fragment.SignUserDateOfBirthFragment
import com.ctu.cashstudy.feature.presentation.sign_up.fragment.SignUserGenderFragment
import com.ctu.cashstudy.feature.presentation.sign_up.fragment.SignUserNameFragment

class SignFragmentStateAdapter(fragmentActivity : FragmentActivity)
    : FragmentStateAdapter(fragmentActivity)
{
    override fun getItemCount(): Int  = 4

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> SignUserNameFragment()
           1 -> SignUserGenderFragment()
           2 -> SignUserDateOfBirthFragment()
           3 -> SignUserCategory()
           else -> SignUserNameFragment()
       }
    }
}