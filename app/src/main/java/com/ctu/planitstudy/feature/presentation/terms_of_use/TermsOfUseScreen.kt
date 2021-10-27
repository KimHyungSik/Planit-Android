package com.ctu.planitstudy.feature.presentation.terms_of_use

import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityTermsOfUseBinding

class TermsOfUseScreen
    : BaseBindingActivity<ActivityTermsOfUseBinding>()
{
    override val bindingInflater: (LayoutInflater) -> ActivityTermsOfUseBinding
            = ActivityTermsOfUseBinding::inflate

    override fun setup() {
    }
}