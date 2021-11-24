package com.ctu.planitstudy.feature.presentation.timer

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityTimerScreenBinding

class TimerScreen : BaseBindingActivity<ActivityTimerScreenBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityTimerScreenBinding
        get() = ActivityTimerScreenBinding::inflate

    @SuppressLint("ClickableViewAccessibility")
    override fun setup() {
        binding.timerCircularBar.setOnTouchListener { v, event -> true }
    }
}
