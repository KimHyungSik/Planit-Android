package com.ctu.planitstudy.feature.presentation.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBottomSheetFragment
import com.ctu.planitstudy.databinding.DialogBottomCalendarBinding

class BottomSheetCalendarDialog : BaseBottomSheetFragment<DialogBottomCalendarBinding>() {

    override val bindingInflater: (LayoutInflater) -> DialogBottomCalendarBinding
        get() = DialogBottomCalendarBinding::inflate

    interface BottomSheetCalendar {
        fun onConfirmedClick()
        fun onChangeDate(year: Int, month: Int, dayOfMonth: Int)
    }

    private var currentDate: Long? = null
    lateinit var bottomSheetCalendar: BottomSheetCalendar

    fun setDialogListener(bottomSheetCalendar: BottomSheetCalendar) {
        this.bottomSheetCalendar = bottomSheetCalendar
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        currentDate = arguments?.getLong("date")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (currentDate != null)
            binding.bottomSheetDatePicker.date = currentDate as Long

        binding.bottomSheetConfirmedDateBtn.setOnClickListener {
            bottomSheetCalendar.onConfirmedClick()
            dismiss()
        }

        binding.bottomSheetDatePicker.setOnDateChangeListener { _, year, month, dayOfMonth ->
            bottomSheetCalendar.onChangeDate(year, month, dayOfMonth)
        }
    }
}
