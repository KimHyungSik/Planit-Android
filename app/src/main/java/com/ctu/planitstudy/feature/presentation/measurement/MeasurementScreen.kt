package com.ctu.planitstudy.feature.presentation.measurement

import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityMeasurementTimerBinding

class MeasurementScreen: BaseBindingActivity<ActivityMeasurementTimerBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMeasurementTimerBinding
        get() = ActivityMeasurementTimerBinding::inflate

    override fun setup() {
    }
}