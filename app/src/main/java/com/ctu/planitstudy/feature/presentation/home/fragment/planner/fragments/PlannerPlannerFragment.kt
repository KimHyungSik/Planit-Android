package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.daysOfWeekFromLocale
import com.ctu.planitstudy.databinding.CalendarHeaderBinding
import com.ctu.planitstudy.databinding.CalendarLayoutBinding
import com.ctu.planitstudy.databinding.FragmentPlannerPlannerBinding
import com.google.android.material.internal.ViewUtils.dpToPx
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@SuppressLint("RestrictedApi")
class Example6MonthView(context: Context) : CardView(context) {

    init {
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.navy_blue_back_ground))
        radius = dpToPx(context,8 ).toFloat()
        elevation = 8f
    }
}

class PlannerPlannerFragment : BaseFragment<FragmentPlannerPlannerBinding>() {
    override val bindingInflater: (LayoutInflater) -> FragmentPlannerPlannerBinding
        get() = FragmentPlannerPlannerBinding::inflate

    val titleRes: String = "title"

    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")

    @SuppressLint("RestrictedApi")
    override fun setInit() {
        super.setInit()
        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)

        binding.exSixCalendar.apply {
            // We want the immediately following/previous month to be
            // partially visible so we multiply the total width by 0.73
            val monthWidth = (dm.widthPixels * 0.73).toInt()
            val dayWidth = monthWidth / 7
            val dayHeight = (dayWidth * 1.73).toInt() // We don't want a square calendar.
            daySize = Size(dayWidth, dayHeight)

            // Add margins around our card view.
            val horizontalMargin = dpToPx(requireContext(), 8 ).toInt()
            val verticalMargin = dpToPx(requireContext(), 14 ).toInt()
            setMonthMargins(start = horizontalMargin, end = horizontalMargin, top = verticalMargin, bottom = verticalMargin)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarLayoutBinding.bind(view).exSixDayText
        }
        binding.exSixCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                val textView = container.textView

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.text = day.date.dayOfMonth.toString()
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.INVISIBLE
                }
            }
        }

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.exSixCalendar.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
        binding.exSixCalendar.scrollToMonth(currentMonth)

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val binding = CalendarHeaderBinding.bind(view)
            val textView = binding.exSixMonthText
            val legendLayout = binding.legendLayout.root
        }
        binding.exSixCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text = titleFormatter.format(month.yearMonth)
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
                        tv.text = daysOfWeek[index].name.first().toString()
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
                        tv.setTextColor(R.color.text_color)
                    }
                }
            }
        }
    }

}