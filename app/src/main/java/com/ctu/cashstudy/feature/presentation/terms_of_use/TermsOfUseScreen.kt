package com.ctu.cashstudy.feature.presentation.terms_of_use

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ctu.cashstudy.core.base.BaseBindingActivity
import com.ctu.cashstudy.databinding.ActivityLoginScreenBinding
import com.ctu.cashstudy.databinding.ActivityTermsOfUseBinding

class TermsOfUseScreen
    : BaseBindingActivity<ActivityTermsOfUseBinding>()
{
    override val bindingInflater: (LayoutInflater) -> ActivityTermsOfUseBinding
            = ActivityTermsOfUseBinding::inflate

    override fun setup() {
    }
}