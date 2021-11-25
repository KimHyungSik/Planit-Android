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
import com.ctu.planitstudy.databinding.DialogFragmentSingleTitleDdayBinding
import com.ctu.planitstudy.databinding.DialogFragmentTimerStartBinding
import com.ctu.planitstudy.databinding.DialogFragmentTimerStopCheckBinding
import com.ctu.planitstudy.feature.presentation.dday.DdayViewModel
import com.ctu.planitstudy.feature.presentation.timer.TimerViewModel

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
        binding.studyTimerStopCheckCancel.setOnClickListener {
            this.dismiss()
        }
        binding.studyTimerStopCheckConfirmed.setOnClickListener{
            viewModel.stopTimer()
            this.dismiss()
        }
    }
}
