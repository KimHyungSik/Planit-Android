package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.daysOfWeekFromLocale
import com.ctu.planitstudy.core.util.setColor
import com.ctu.planitstudy.databinding.FragmentPlannerPlannerBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.home.HomeViewModel
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.calendar.DayViewContainer
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.calendar.MonthViewContainer
import com.ctu.planitstudy.feature.presentation.recycler.study.InStudyListRecycler
import com.ctu.planitstudy.feature.presentation.recycler.study.StudyListMode
import com.ctu.planitstudy.feature.presentation.recycler.study.StudyListRecyclerAdapter
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.kizitonwose.calendarview.model.*
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.utils.Size
import com.kizitonwose.calendarview.utils.yearMonth
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class PlannerPlannerFragment :
    BaseFragment<FragmentPlannerPlannerBinding, PlannerPlannerViewModel>(), InStudyListRecycler {
    override val bindingInflater: (LayoutInflater) -> FragmentPlannerPlannerBinding
        get() = FragmentPlannerPlannerBinding::inflate

    val TAG = "PlannerPlanner - 로그"

    var monthToWeek = false

    override val viewModel by activityViewModels<PlannerPlannerViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()

    private lateinit var studyListRecyclerAdapter: StudyListRecyclerAdapter

    private val titleFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월")

    private var minCurrentDate = LocalDate.now()

    @SuppressLint("RestrictedApi")
    override fun setInit() {
        super.setInit()

        studyListRecyclerAdapter = StudyListRecyclerAdapter(this)

        with(binding) {
            plannerPlannerCalendarArrow.setOnClickListener {

                if (monthToWeek) {
                    plannerPlannerCustomCalendar.updateMonthConfiguration(
                        inDateStyle = InDateStyle.ALL_MONTHS,
                        maxRowCount = 1,
                        hasBoundaries = true
                    )
                    plannerPlannerCalendarArrow.setImageResource(R.drawable.ic_arrow_down)
                } else {
                    plannerPlannerCustomCalendar.updateMonthConfiguration(
                        outDateStyle = OutDateStyle.END_OF_GRID,
                        maxRowCount = 6,
                        hasBoundaries = true
                    )
                    plannerPlannerCalendarArrow.setImageResource(R.drawable.ic_arrow_up)
                }
                binding.plannerPlannerCustomCalendar.scrollToDate(viewModel.plannerState.value!!.checkDate)
                monthToWeek = !monthToWeek
            }

            with(plannerPlannerStudyList) {
                layoutManager = LinearLayoutManager(context)
                adapter = studyListRecyclerAdapter
            }
        }

        binding.plannerPlannerCustomCalendar.apply {
            val defaultSize = daySize
            daySize = Size(defaultSize.width, defaultSize.width)
            updateMonthConfiguration(
                inDateStyle = InDateStyle.ALL_MONTHS,
                maxRowCount = 1,
                hasBoundaries = true
            )
        }

        // 캘린더 날짜 커스텀
        binding.plannerPlannerCustomCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            @SuppressLint("ResourceAsColor")
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                val textView = container.textView
//                val imageView = container.imageView
                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.text = day.date.dayOfMonth.toString()
//                    textView.visibility = View.VISIBLE
                    if (day.date == LocalDate.now())
                        textView.setTextColor(setColor(R.color.point_color))
                    else
                        textView.setTextColor(setColor(R.color.text_color))
                    if (day.date == viewModel.plannerState.value!!.checkDate)
                        context?.let { it1 ->
                            textView.background =
                                (ContextCompat.getDrawable(it1, R.drawable.subcolor_circle_background))
                        }
                    else
                        textView.setBackgroundColor(setColor(R.color.module_color))

                    container.view.setOnClickListener {
                        viewModel.updatePlannerState(
                            viewModel.plannerState.value!!.copy(
                                checkDate = day.date
                            )
                        )
                        binding.plannerPlannerCustomCalendar.notifyCalendarChanged()
                    }
                } else {
                    textView.visibility = View.INVISIBLE
                }
            }
        }

        binding.plannerPlannerCustomCalendar.monthScrollListener = {
            binding.plannerPlannerCalendarMonthText.text =
                titleFormatter.format(it.yearMonth)
        }

        // 캘린더 년월 커스텀
        binding.plannerPlannerCustomCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            @SuppressLint("ResourceAsColor")
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
            }
        }
        setCalendarDate()
        homeViewModelSetUp()
    }

    private fun setCalendarDate() {
        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.plannerPlannerCustomCalendar.setup(
            currentMonth.minusMonths(10),
            currentMonth.plusMonths(10),
            daysOfWeek.first()
        )
        binding.plannerPlannerCustomCalendar.scrollToDate(viewModel.plannerState.value!!.checkDate)
    }

    private fun homeViewModelSetUp() {
        homeViewModel.homeState.observe(this, {

            if (it.studyListDto.studies.isEmpty()) {
                binding.studyFragmentEmptyImg.visibility = View.VISIBLE
                binding.plannerPlannerStudyList.visibility = View.GONE
            } else {
                binding.studyFragmentEmptyImg.visibility = View.GONE
                binding.plannerPlannerStudyList.visibility = View.VISIBLE
            }

            studyListRecyclerAdapter.submitList(it.studyListDto, StudyListMode.PlannerStudyListMode)
            studyListRecyclerAdapter.notifyDataSetChanged()
        })

        viewModel.plannerState.observe(this, {
            homeViewModel.changeStudyDate(it.checkDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        })
    }

    fun addStudyScreen() {
        moveIntent(Screens.StudyScreenSh.activity)
    }

    override fun onClickedItem(position: Int) {
        val intent = Intent(activity, Screens.StudyScreenSh.activity)
        intent.putExtra("studyDto", studyListRecyclerAdapter.studyList.studies[position])
        moveIntent(intent)
    }

    override fun onClickedCheckbox(position: Int, check: Boolean) {
        homeViewModel.changeStudyIsDone(studyListRecyclerAdapter.studyList.studies[position].studyId.toString(), check)
    }
}
