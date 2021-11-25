package com.ctu.planitstudy.feature.presentation.home.fragment.my

import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentMyBinding

class MyFragment : BaseFragment<FragmentMyBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentMyBinding
        get() = FragmentMyBinding::inflate
}
