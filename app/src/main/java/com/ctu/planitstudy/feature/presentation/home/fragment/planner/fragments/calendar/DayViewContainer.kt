package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.calendar

import android.view.View
import com.ctu.planitstudy.databinding.CalendarLayoutBinding
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = CalendarLayoutBinding.bind(view).plannerPlannerCustomCalendarDayText
//    val imageView = CalendarLayoutBinding.bind(view).plannerPlannerCustomCalendarDayBackGround
}
