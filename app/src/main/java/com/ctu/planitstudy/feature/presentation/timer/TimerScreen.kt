package com.ctu.planitstudy.feature.presentation.timer

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityTimerScreenBinding

class TimerScreen : BaseBindingActivity<ActivityTimerScreenBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityTimerScreenBinding
        get() = ActivityTimerScreenBinding::inflate

    private val viewModel: TimerViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun setup() {
        with(binding) {
            with(timerCircularBar) {
                setOnTouchListener { v, event -> true }
                max = 3600f
            }
        }

        viewModel.timerState.observe(this, {
            binding.timerTimeText.text = it.timeString
            binding.timerCircularBar.progress = it.time.toFloat()
        })
    }
}
