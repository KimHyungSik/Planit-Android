package com.ctu.planitstudy.feature.presentation.home.fragment.my

import android.content.Intent
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.databinding.FragmentMyBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding>() {

    private val viewModel by activityViewModels<MyViewModel>()

    override val bindingInflater: (LayoutInflater) -> FragmentMyBinding
        get() = FragmentMyBinding::inflate

    override fun setInit() {
        super.setInit()
        with(binding) {
            viewmodel = viewModel
            activity = this@MyFragment
            myFragmentEditUserImg.setOnClickListener {
                val intent = Intent(context, Screens.EditUserScreenSh.activity)
                intent.putExtra("user", viewModel.userInformationDto.value)
                moveIntent(intent)
            }
        }
        with(viewModel) {
            userInformationDto.observe(this@MyFragment, {
                binding.invalidateAll()
            })
        }
    }

    fun logout() {
        CashStudyApp.prefs.accessToken = ""
        CashStudyApp.prefs.refreshToken = ""
        moveIntentAllClear(Screens.LoginScreenSh.activity)
    }
}
