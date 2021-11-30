package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.daysOfWeekFromLocale
import com.ctu.planitstudy.databinding.FragmentPlannerPlannerBinding
import com.ctu.planitstudy.feature.presentation.home.fragment.home.HomeViewModel
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.calendar.DayViewContainer
import com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments.calendar.MonthViewContainer
import com.ctu.planitstudy.feature.presentation.recycler.study.InStudyListRecycler
import com.ctu.planitstudy.feature.presentation.recycler.study.StudyListMode
import com.ctu.planitstudy.feature.presentation.recycler.study.StudyListRecyclerAdapter
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.utils.Size
import com.kizitonwose.calendarview.utils.yearMonth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PlannerPlannerFragment : BaseFragment<FragmentPlannerPlannerBinding>(), InStudyListRecycler {
    override val bindingInflater: (LayoutInflater) -> FragmentPlannerPlannerBinding
        get() = FragmentPlannerPlannerBinding::inflate

    val TAG = "PlannerPlanner - 로그"

    var mothToWeek = false

    private val viewModel by activityViewModels<PlannerPlannerViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()

    private lateinit var studyListRecyclerAdapter: StudyListRecyclerAdapter

    private val titleFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월")

    @SuppressLint("RestrictedApi")
    override fun setInit() {
        super.setInit()
        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)

        studyListRecyclerAdapter = StudyListRecyclerAdapter(this)

        with(binding) {
            activity = this@PlannerPlannerFragment
            plannerPlannerCalendarDownArrow.setOnClickListener {
                if (mothToWeek)
                    plannerPlannerCustomCalendar.updateMonthConfiguration(
                        maxRowCount = 1,
                    )
                else
                    plannerPlannerCustomCalendar.updateMonthConfiguration(
                        maxRowCount = 6,
                    )
                setCalendarDate()
                mothToWeek = !mothToWeek
            }

            with(plannerPlannerStudyList) {
                layoutManager = LinearLayoutManager(context)
                adapter = studyListRecyclerAdapter
            }
        }

        binding.plannerPlannerCustomCalendar.apply {
            val monthWidth = (dm.widthPixels).toInt()
            val dayWidth = monthWidth / 7
            val dayHeight = dayWidth // We don't want a square calendar.
            daySize = Size(dayWidth, dayHeight)

            updateMonthConfiguration(
                maxRowCount = 1,
            )
        }

        // 캘린더 날짜 커스텀
        binding.plannerPlannerCustomCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            @SuppressLint("ResourceAsColor")
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                val textView = container.textView

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.text = day.date.dayOfMonth.toString()
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.INVISIBLE
                }

                if (day.date == LocalDate.now())
                    textView.setTextColor(getResources().getColor(R.color.point_color))
                if (day.date == viewModel.plannerState.value!!.checkDate)
                    context?.let { it1 ->
                        textView.background =
                            ContextCompat.getDrawable(it1, R.drawable.subcolor_circle_background)
                    }
                else
                    textView.setBackgroundColor(getResources().getColor(R.color.item_guide_stroke))
                container.view.setOnClickListener {
                    viewModel.updatePlannerState(
                        viewModel.plannerState.value!!.copy(
                            checkDate = day.date
                        )
                    )
                    binding.plannerPlannerCustomCalendar.notifyCalendarChanged()
                }
            }
        }

        // 캘린더 년월 커스텀
        binding.plannerPlannerCustomCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)

            @SuppressLint("ResourceAsColor")
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text = titleFormatter.format(month.yearMonth)
            }
        }
        setCalendarDate()
        homeViewModelSetUp()
    }

    fun setCalendarDate() {
        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = viewModel.plannerState.value!!.checkDate.yearMonth
        binding.plannerPlannerCustomCalendar.setup(
            currentMonth.minusMonths(10),
            currentMonth.plusMonths(10),
            daysOfWeek.first()
        )
        binding.plannerPlannerCustomCalendar.scrollToDate(LocalDate.now())
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

    fun addStudyScreen(){
        moveIntent(Screens.StudyScreenSh.activity)
    }

    override fun onClickedItem(position: Int) {
        val intent = Intent(activity, Screens.StudyScreenSh.activity)
        intent.putExtra("studyDto", studyListRecyclerAdapter.studyList.studies[position])
        moveIntent(intent)
    }
}
