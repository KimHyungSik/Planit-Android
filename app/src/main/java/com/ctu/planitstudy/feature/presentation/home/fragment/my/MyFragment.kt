package com.ctu.planitstudy.feature.presentation.home.fragment.my

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.EmailUtils
import com.ctu.planitstudy.databinding.FragmentMyBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.dialogs.ReadyDialog
import com.ctu.planitstudy.feature.presentation.home.fragment.my.dialog.LogoutDialog
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
            logout.observe(this@MyFragment, {
                if (it)
                    logout()
            })
        }
    }

    fun showLogoutDialog() {
        LogoutDialog().show(
            parentFragmentManager, "LogoutDialog"
        )
    }

    fun logout() {
        CashStudyApp.prefs.accessToken = ""
        CashStudyApp.prefs.refreshToken = ""
        moveIntentAllClear(Screens.LoginScreenSh.activity)
    }

    fun email(){
        context?.let { EmailUtils.sendEmail(it, "플래닛 문의", arrayOf("planit.timer@gmail.com")) }
    }

    fun showReadyDialog()
    {
        ReadyDialog().show(
            parentFragmentManager, "ReadyDialog"
        )
    }

    fun termsOfService(){
       moveIntent(Screens.TermsOfUseSh.activity)
    }
}
