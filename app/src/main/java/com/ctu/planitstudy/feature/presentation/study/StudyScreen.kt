package com.ctu.planitstudy.feature.presentation.study

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.Weekday
import com.ctu.planitstudy.databinding.ActivityStudyScreenBinding
import com.ctu.planitstudy.feature.presentation.dialogs.EmptyTitleCheckDialog
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class StudyScreen : BaseBindingActivity<ActivityStudyScreenBinding>() {

    val TAG = "StudyScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivityStudyScreenBinding
        get() = ActivityStudyScreenBinding::inflate

    private val viewModel: StudyViewModel by viewModels()

    private val disposables = CompositeDisposable()

    private val checkBoxList = ArrayList<CheckBox>()

    override fun setup() {
        binding.apply {
            viewmodel = viewModel

            checkBoxList.add(studyAllDay)
            checkBoxList.add(studySunDay)
            checkBoxList.add(studyMonDay)
            checkBoxList.add(studyTuesDay)
            checkBoxList.add(studyWednesDay)
            checkBoxList.add(studyThursDay)
            checkBoxList.add(studyFriDay)
            checkBoxList.add(studySaturDay)

            activationWeekCheckBox(
                viewModel.studyState.value!!.activationWeek
            )

            studyBackScreenBtn.setOnClickListener {
                finish()
            }

            studyDateItemView.setOnClickListener {
                studyCustomDatePicker.visibility = View.VISIBLE
                studyBlur.visibility = View.VISIBLE
                updateStudyState(
                    viewModel.studyState.value!!.copy(
                        kindDate = KindStudyDate.SingleDate
                    )
                )
                studyDatePicker.date =
                    DateConvter.textDateToLongDate(viewModel.studyState.value!!.singleAt)
            }

            // 시작일 선택
            studyRepeatStartDateItemView.setOnClickListener {
                studyCustomDatePicker.visibility = View.VISIBLE
                studyBlur.visibility = View.VISIBLE
                updateStudyState(
                    viewModel.studyState.value!!.copy(
                        kindDate = KindStudyDate.StartAt
                    )
                )
                studyDatePicker.date =
                    DateConvter.textDateToLongDate(viewModel.studyState.value!!.startAt)
            }
            // 종료일 선택
            studyRepeatEndDateItemView.setOnClickListener {
                studyCustomDatePicker.visibility = View.VISIBLE
                studyBlur.visibility = View.VISIBLE
                updateStudyState(
                    viewModel.studyState.value!!.copy(
                        kindDate = KindStudyDate.EndAt
                    )
                )
                studyDatePicker.date =
                    DateConvter.textDateToLongDate(viewModel.studyState.value!!.endAt)
            }

            // 데이터 피커 확인
            studyConfirmedDateBtn.setOnClickListener {
                studyCustomDatePicker.visibility = View.INVISIBLE
                studyBlur.visibility = View.INVISIBLE
                binding.invalidateAll()
                viewModel.studyUpdate(
                    viewModel.studyState.value!!.copy(
                        activationWeek = DateCalculation().calDateBetweenWeek(
                            DateConvter.textDateToDtoDate(viewModel.studyState.value!!.startAt),
                            DateConvter.textDateToDtoDate(viewModel.studyState.value!!.endAt)
                        )
                    )
                )
                activationWeekCheckBox(
                    viewModel.studyState.value!!.activationWeek
                )
            }

            // 데이터 피커 변경
            studyDatePicker.apply {
                setOnDateChangeListener { view, year, month, dayOfMonth ->
                    viewmodel!!.studyDateUpdate(
                        DateConvter.dtoDateToTextDate("$year-${month + 1}-$dayOfMonth"),
                        viewModel.studyState.value!!.kindDate
                    )
                    binding.studyAllDay.isChecked = false
                    viewModel.clearCheckWeek()
                }
            }

            studyRepeatSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                updateStudyState(
                    viewModel.studyState.value!!.copy(
                        repeat = isChecked
                    )
                )
                if (!isChecked) {
                    studyDateItemView.visibility = View.VISIBLE
                    studyRepeatDateItemView.visibility = View.GONE
                } else {
                    studyDateItemView.visibility = View.GONE
                    studyRepeatDateItemView.visibility = View.VISIBLE
                }
            }
        }

        viewModel.apply {
            studyState.observe(this@StudyScreen, {
                Log.d(TAG, "setup: $it")
                // 매일을 제외한 모든 날을 선택시 매일 을 활성화
                // 나머지 요일을 비활성화
                if (listsEqual(
                        it.activationWeek, it.week
                    )
                ) {
                    viewModel.clearCheckWeek()
                    viewModel.changeCheckWeek(Weekday.All.ordinal, true)
                }
                checkBoxList.forEachIndexed { index, checkbox ->
                    checkbox.isChecked = it.week.contains(Weekday.values()[index])
                }
            })
        }

        // 팝업 상태 관리
        viewModel.studyDialogState.observe(this, {
            if(it.emptyTitleDialog)
                EmptyTitleCheckDialog().show(
                    supportFragmentManager, "EmptyTitleCheckDialog"
                )
        })

        disposables.addAll(
            RxTextView.textChanges(binding.studyEditTitle)
                .subscribe {
                    binding.studyTitleLengthCount.text = it.length.toString() + "/10"
                }
        )
    }

    @SuppressLint("ResourceType")
    fun activationWeekCheckBox(activationWeek: ArrayList<Weekday>) {
        for (checkBox in checkBoxList) {
            if (checkBox == binding.studyAllDay) continue
            checkBox.apply {
                isEnabled = false
                background =
                    ContextCompat.getDrawable(this@StudyScreen, R.drawable.enabled_check_box)
            }
        }

        for (week in activationWeek) {
            binding.apply {
                when (week) {
                    Weekday.Monday -> {
                        studyMonDay.isEnabled = true
                        studyMonDay.background = ContextCompat.getDrawable(
                            this@StudyScreen,
                            R.drawable.dday_color_radio_button
                        )
                    }
                    Weekday.Tuesday -> {
                        studyTuesDay.isEnabled = true
                        studyTuesDay.background = ContextCompat.getDrawable(
                            this@StudyScreen,
                            R.drawable.dday_color_radio_button
                        )
                    }
                    Weekday.Wednesday -> {
                        studyWednesDay.isEnabled = true
                        studyWednesDay.background = ContextCompat.getDrawable(
                            this@StudyScreen,
                            R.drawable.dday_color_radio_button
                        )
                    }
                    Weekday.Thursday -> {
                        studyThursDay.isEnabled = true
                        studyThursDay.background = ContextCompat.getDrawable(
                            this@StudyScreen,
                            R.drawable.dday_color_radio_button
                        )
                    }
                    Weekday.Friday -> {
                        studyFriDay.isEnabled = true
                        studyFriDay.background = ContextCompat.getDrawable(
                            this@StudyScreen,
                            R.drawable.dday_color_radio_button
                        )
                    }
                    Weekday.Saturday -> {
                        studySaturDay.isEnabled = true
                        studySaturDay.background = ContextCompat.getDrawable(
                            this@StudyScreen,
                            R.drawable.dday_color_radio_button
                        )
                    }
                    Weekday.Sunday -> {
                        studySunDay.isEnabled = true
                        studySunDay.background = ContextCompat.getDrawable(
                            this@StudyScreen,
                            R.drawable.dday_color_radio_button
                        )
                    }
                }
            }
        }
    }

    fun updateStudyState(studyState: StudyState) {
        viewModel.studyUpdate(studyState)
    }

    fun listsEqual(list1: List<Any>, list2: List<Any>): Boolean {

        if (list1.size != list2.size)
            return false

        val pairList = list1.zip(list2)

        return pairList.all { (elt1, elt2) ->
            elt1 == elt2
        }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}