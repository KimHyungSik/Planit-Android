package com.ctu.planitstudy.feature.presentation.planitpass.fragment

import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentPlanitPassTicketBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.planitpass.PlanitPassViewModel

class FragmentFunPlanit : BaseFragment<FragmentPlanitPassTicketBinding, PlanitPassViewModel>() {
    override val bindingInflater: (LayoutInflater) -> FragmentPlanitPassTicketBinding
        get() = FragmentPlanitPassTicketBinding::inflate
    override val viewModel: PlanitPassViewModel by activityViewModels<PlanitPassViewModel>()

    override fun setInit() {
        super.setInit()
        binding.planitPassTicketImg.setImageDrawable(ContextCompat.getDrawable(CashStudyApp.instance, R.drawable.ic_fullmoon))
    }
}