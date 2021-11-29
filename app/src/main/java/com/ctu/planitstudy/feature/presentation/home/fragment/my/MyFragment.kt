package com.ctu.planitstudy.feature.presentation.home.fragment.my

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentMyBinding
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding>() {

    private val viewModel by activityViewModels<MyViewModel>()

    override val bindingInflater: (LayoutInflater) -> FragmentMyBinding
        get() = FragmentMyBinding::inflate

    override fun setInit() {
        super.setInit()
        binding.viewmodel = viewModel
        binding.myFragmentEditUserImg.setOnClickListener {
            moveIntent(Screens.EditUserScreenSh.activity)
        }
    }
}
