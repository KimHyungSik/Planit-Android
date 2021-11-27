package com.ctu.planitstudy.feature.presentation.timer.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.core.util.longToTimeShortString
import com.ctu.planitstudy.databinding.DialogFragmentTimerBrakeTimeBinding
import com.ctu.planitstudy.databinding.DialogFragmentTimerStopCheckBinding
import com.ctu.planitstudy.feature.presentation.timer.TimerCycle
import com.ctu.planitstudy.feature.presentation.timer.TimerViewModel
import java.util.*

class TimerBreakTimeDialog : DialogFragment() {

    var timer: Timer = Timer()
    private var breakTime = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private lateinit var binding: DialogFragmentTimerBrakeTimeBinding
    private val viewModel: TimerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentTimerBrakeTimeBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = kotlin.concurrent.timer(period = 1000) {
            binding.timerBreakTimeText.text = breakTime.longToTimeShortString()
            breakTime--
            if(breakTime <= 0){
                timer.cancel()
                viewModel.startTimer()
                this@TimerBreakTimeDialog.dismiss()
            }
        }

        binding.timerBreakConfirm.setOnClickListener {
            viewModel.startTimer()
            this.dismiss()
        }
    }
}
