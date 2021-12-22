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
import com.ctu.planitstudy.feature.presentation.timer.TimerCycle
import com.ctu.planitstudy.feature.presentation.timer.TimerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer

class TimerBreakTimeDialog : DialogFragment() {

    val TAG = "TimerBreak - 로그"

    var timer: Timer = Timer()
    private var breakTime = 300L

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
        viewModel.updateTimerState(
            viewModel.timerState.value!!.copy(
                breakTime = viewModel.timerState.value!!.breakTime + 1
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = kotlin.concurrent.timer(period = 1000) {
            CoroutineScope(Dispatchers.Main).launch() {
                binding.timerBreakTimeText.text = breakTime.longToTimeShortString()
                breakTime--
                if (breakTime <= 0) {
                    timer.cancel()
                    viewModel.changeTimerCycle(TimerCycle.TimeFlow)
                    this@TimerBreakTimeDialog.dismiss()
                }
            }
        }
        binding.timerBreakConfirm.setOnClickListener {
            viewModel.changeTimerCycle(TimerCycle.TimeFlow)
            timer.cancel()
            this.dismiss()
        }
    }

    override fun onDestroy() {

        super.onDestroy()
    }
}
