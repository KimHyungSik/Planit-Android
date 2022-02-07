package com.ctu.planitstudy.feature.presentation.timer.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.databinding.DialogFragmentTimerStopCheckBinding
import com.ctu.planitstudy.feature.presentation.timer.TimerCycle
import com.ctu.planitstudy.feature.presentation.timer.TimerViewModel
import java.lang.StringBuilder

class TimerStopDialog : DialogFragment() {

    val TAG = "Representative - 로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private lateinit var binding: DialogFragmentTimerStopCheckBinding
    private val viewModel: TimerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentTimerStopCheckBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.studyTimerStopDialogSubTitle.text = buildString{
            append("보너스 티켓까지 앞으로\n")
            viewModel.timerState.value?.let {
                append((3660 - it.time) / 60)
            }
            append("분 남았어요!")
        }
        binding.studyTimerStopCheckCancel.setOnClickListener {
            viewModel.changeTimerCycle(TimerCycle.TimeFlow)
            this.dismiss()
        }
        binding.studyTimerStopCheckConfirmed.setOnClickListener {
            viewModel.changeTimerCycle(TimerCycle.TimeStop)
            this.dismiss()
        }
        binding.studyBrackTime.setOnClickListener {

            viewModel.changeTimerCycle(TimerCycle.TimeBreak)
            this.dismiss()
        }
    }
}
