package com.ctu.planitstudy.feature.presentation.measurement

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityMeasurementTimerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeasurementScreen: BaseBindingActivity<ActivityMeasurementTimerBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMeasurementTimerBinding
        get() = ActivityMeasurementTimerBinding::inflate

    val viewModel : MeasurementViewModel by viewModels()

    override fun setup() {
    }
}