package com.ctu.planitstudy.feature.presentation.home.fragment.analysis.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctu.planitstudy.core.base.BaseFragment
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.dp
import com.ctu.planitstudy.core.util.longToTimeShortString
import com.ctu.planitstudy.databinding.FragmentAnalysisDailyReportBinding
import com.ctu.planitstudy.feature.data.remote.dto.study.StudyDto
import com.ctu.planitstudy.feature.presentation.dialogs.BottomSheetCalendarDialog
import com.ctu.planitstudy.feature.presentation.home.fragment.analysis.AnalysisViewModel
import com.ctu.planitstudy.feature.presentation.home.fragment.analysis.recycler.AchievementRateListRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class AnalysisDailyReportFragment :
    BaseFragment<FragmentAnalysisDailyReportBinding, AnalysisViewModel>(),
    BottomSheetCalendarDialog.BottomSheetCalendar {

    val TAG = "AnalysisFragment - 로그"

    override val bindingInflater: (LayoutInflater) -> FragmentAnalysisDailyReportBinding
        get() = FragmentAnalysisDailyReportBinding::inflate

    override val viewModel: AnalysisViewModel by viewModels()

    private lateinit var achievementRateListRecyclerAdapter: AchievementRateListRecyclerAdapter
    private var currentDate = LocalDate.now().toString()
    var currentDateText = DateConvter.dtoDateToTextDate(currentDate)
    var totalTime = "00시간00분00초 공부했어요"
    var mostStudyTitle = "제목"
    var mostStudyTime = "00시간00분00초"
    var studyCounter = "0/0"
    var studyIsDonePercent = "0%"
    private val calendarDialog = BottomSheetCalendarDialog()

    override fun setInit() {
        super.setInit()
        achievementRateListRecyclerAdapter = AchievementRateListRecyclerAdapter()

        with(binding) {
            activity = this@AnalysisDailyReportFragment
            analysisFragmentStudyTimeLineRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = achievementRateListRecyclerAdapter
            }
            analysisFragmentDateText.text = DateConvter.dtoDateToTextDate(currentDate)
            analysisFragmentDateText.setOnClickListener {
                showCalendar(DateConvter.dtoDateTOLong(currentDate))
            }
        }
        viewModel.studyDto.observe(this, {

            currentDateText = DateConvter.dtoDateToTextDate(currentDate)
            if (it.studies.isNotEmpty()) {
                listEmptyView(false)

                val params = binding.analysisFragmentStudyTimeLineRecyclerView.layoutParams
                params.height = ((128 * it.studies.size).dp).coerceAtMost((304).dp)
                binding.analysisFragmentStudyTimeLineRecyclerView.layoutParams = params

                var tempTotalTime: Int = 0
                var mostStudy: StudyDto? = null
                var studyIsDone = 0

                for (n in it.studies) {
                    studyIsDone += if (n.isDone) 1 else 0
                    tempTotalTime += n.recordedTime
                    if (mostStudy == null) mostStudy = n
                    else {
                        if (mostStudy.recordedTime < n.recordedTime) mostStudy = n
                    }
                }

                totalTime = "${tempTotalTime.toLong().longToTimeShortString()} 공부했어요"
                mostStudyTitle = mostStudy!!.title
                mostStudyTime = mostStudy!!.recordedTime.toLong().longToTimeShortString()
                studyCounter = "$studyIsDone/${it.studies.size}"
                val percent = ((studyIsDone / it.studies.size) * 100).toInt()
                studyIsDonePercent = "$percent%"
                binding.analysisFragmentStudyTimeLineProgress.max = it.studies.size
                binding.analysisFragmentStudyTimeLineProgress.progress = studyIsDone

                // 공부는 있지만 측정 시간이 없는 경우
                if (tempTotalTime == 0) {
                    listEmptyView(true)
                    with(binding) {
                        achievementRateEmptyImg.visibility = View.GONE
                        analysisFragmentAchievementRateCheckTitle.visibility = View.VISIBLE
                        analysisFragmentAchievementRateCheckColumn.visibility = View.VISIBLE
                        analysisFragmentAchievementRateCheckImg.visibility = View.VISIBLE
                        analysisFragmentTotalStudyTimeTitle.visibility = View.VISIBLE
                    }
                    totalTime = "타이머 측정 시간이 없습니다"
                }
            } else {
                listEmptyView(true)
                totalTime = "타이머 측정 시간이 없습니다"
            }
            binding.invalidateAll()
            achievementRateListRecyclerAdapter.submitList(it)
            achievementRateListRecyclerAdapter.notifyDataSetChanged()
        })

        viewModel.getStudyList(currentDate)
    }

    override fun onConfirmedClick() {
        viewModel.getStudyList(currentDate)
    }

    override fun onChangeDate(year: Int, month: Int, dayOfMonth: Int) {
        currentDate = buildString {
            append(year)
            append("-")
            if (month + 1 < 10)
                append(0)
            append(month + 1)
            append("-")
            if (dayOfMonth < 10)
                append(0)
            append(dayOfMonth)
        }
    }

    private fun listEmptyView(b: Boolean) {
        with(binding) {
            if (b) {
                achievementRateEmptyImg.visibility = View.VISIBLE

                analysisFragmentAchievementRateCheckColumn.visibility = View.GONE
                analysisFragmentMostFocusedStudyColumn.visibility = View.GONE
                analysisFragmentStudyTimeLineColumn.visibility = View.GONE

                analysisFragmentStudyTimeLineTitle.visibility = View.GONE
                analysisFragmentTotalStudyTimeTitle.visibility = View.GONE
                analysisFragmentMostFocusedStudyTitle.visibility = View.GONE
                analysisFragmentAchievementRateCheckTitle.visibility = View.GONE

                analysisFragmentAchievementRateCheckImg.visibility = View.GONE
            } else {
                achievementRateEmptyImg.visibility = View.GONE

                analysisFragmentAchievementRateCheckColumn.visibility = View.VISIBLE
                analysisFragmentMostFocusedStudyColumn.visibility = View.VISIBLE
                analysisFragmentStudyTimeLineColumn.visibility = View.VISIBLE

                analysisFragmentStudyTimeLineTitle.visibility = View.VISIBLE
                analysisFragmentTotalStudyTimeTitle.visibility = View.VISIBLE
                analysisFragmentMostFocusedStudyTitle.visibility = View.VISIBLE
                analysisFragmentAchievementRateCheckTitle.visibility = View.VISIBLE

                analysisFragmentAchievementRateCheckImg.visibility = View.VISIBLE
            }
        }
    }

    private fun showCalendar(time: Long) {
        val args = Bundle()
        args.putLong("date", time)
        calendarDialog.arguments = args
        calendarDialog.setDialogListener(this)
        calendarDialog.show(parentFragmentManager, "calendarDialog")
    }
}
