package com.ctu.planitstudy.feature.presentation.dday

import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityDdayScreenBinding

class DdayScreen
    :BaseBindingActivity<ActivityDdayScreenBinding>()
{
    override val bindingInflater: (LayoutInflater) -> ActivityDdayScreenBinding
        get() = ActivityDdayScreenBinding::inflate

    override fun setup() {
    }
}