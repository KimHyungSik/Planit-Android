package com.ctu.planitstudy.feature.presentation.study

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.databinding.ActivityStudyScreenBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable

class StudyScreen : BaseBindingActivity<ActivityStudyScreenBinding>() {

    val TAG = "StudyScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityStudyScreenBinding
        get() = ActivityStudyScreenBinding::inflate

    private val viewModel: StudyViewModel by viewModels()
    private var kindDate: KindStudyDate = KindStudyDate.SingleDate

    private val disposables = CompositeDisposable()

    override fun setup() {
        binding.apply {
            viewmodel = viewModel

            studyDateItemView.setOnClickListener {
                studyCustomDatePicker.visibility = View.VISIBLE
                studyBlur.visibility = View.VISIBLE
                kindDate = KindStudyDate.SingleDate
                studyDatePicker.date = DateConvter.textDateToLongDate(viewModel.studyState.value!!.singleAt)
            }

            // 시작일 선택
            studyRepeatStartDateItemView.setOnClickListener {
                studyCustomDatePicker.visibility = View.VISIBLE
                studyBlur.visibility = View.VISIBLE
                kindDate = KindStudyDate.StartAt
                studyDatePicker.date = DateConvter.textDateToLongDate(viewModel.studyState.value!!.startAt)
            }
            // 종료일 선택
            studyRepeatEndDateItemView.setOnClickListener {
                studyCustomDatePicker.visibility = View.VISIBLE
                studyBlur.visibility = View.VISIBLE
                kindDate = KindStudyDate.EndAt
                studyDatePicker.date = DateConvter.textDateToLongDate(viewModel.studyState.value!!.endAt)
            }
            // 데이터 피커 확인
            studyConfirmedDateBtn.setOnClickListener {
                studyCustomDatePicker.visibility = View.INVISIBLE
                studyBlur.visibility = View.INVISIBLE
                binding.invalidateAll()
                Log.d(
                    TAG,
                    "setup: ${
                        DateCalculation().calDateBetweenWeek(
                            DateConvter.textDateToDtoDate(viewModel.studyState.value!!.startAt),
                            DateConvter.textDateToDtoDate(viewModel.studyState.value!!.endAt)
                        )
                    }"
                )
            }

            // 데이터 피커 변경
            studyDatePicker.apply {
                setOnDateChangeListener { view, year, month, dayOfMonth ->
                    viewmodel!!.studyDateUpdate(
                        DateConvter.dtoDateToTextDate("$year-${month + 1}-$dayOfMonth"),
                        kindDate
                    )
                }
            }
        }
        disposables.add(
            RxTextView.textChanges(binding.studyEditTitle)
                .subscribe {
                    binding.studyTitleLengthCount.text = it.length.toString() + "/10"
                })
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}