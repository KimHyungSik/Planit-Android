package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.calendar

import android.view.View
import com.ctu.planitstudy.databinding.CalendarHeaderBinding
import com.kizitonwose.calendarview.ui.ViewContainer

class MonthViewContainer(view: View) : ViewContainer(view) {
    val binding = CalendarHeaderBinding.bind(view)
    val textView = binding.exSixMonthText
}