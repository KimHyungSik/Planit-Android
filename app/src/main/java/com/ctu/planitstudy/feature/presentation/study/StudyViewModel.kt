package com.ctu.planitstudy.feature.presentation.study

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.Weekday

class StudyViewModel : ViewModel() {

    val TAG = "StudyViewModel - 로그"
    private val currentDate = DateConvter.dtoDateToTextDate(null)
    private val tomorrowDate =
        DateConvter.dtoDateToTextDate(DateCalculation().getCurrentDateString(1))
    private val _studyState = MutableLiveData<StudyState>(
        StudyState(
            tomorrowDate, currentDate, tomorrowDate,
            week = arrayListOf(Weekday.All)
        )
    )
    val studyState: LiveData<StudyState> = _studyState

    init {
        _studyState.value = studyState.value!!.copy(
            activationWeek =
            DateCalculation().calDateBetweenWeek(
                DateConvter.textDateToDtoDate(studyState.value!!.startAt),
                DateConvter.textDateToDtoDate(studyState.value!!.endAt)
            )
        )
    }

    fun changeCheckWeek(weekCount: Int, isCheck: Boolean) {
        val newStudyStateWeek = studyState.value!!.week
        val week = Weekday.values()[weekCount]
        if (isCheck && !newStudyStateWeek.contains(week))
            newStudyStateWeek.add(week)
        else
            newStudyStateWeek.remove(week)

        newStudyStateWeek.sortBy { it.ordinal }

        _studyState.value = studyState.value!!.copy(
            week = newStudyStateWeek
        )
    }

    fun clearCheckWeek() {
        _studyState.value = studyState.value!!.copy(
            week = arrayListOf()
        )
    }

    fun studyUpdate(studyState: StudyState) {
        _studyState.value = studyState
    }

    fun studyDateUpdate(date: String, kindStudyDate: KindStudyDate) {
        _studyState.value = when (kindStudyDate) {
            is KindStudyDate.SingleDate -> {
                studyState.value!!.copy(singleAt = date)
            }
            is KindStudyDate.StartAt -> {
                studyState.value!!.copy(startAt = date)
            }
            is KindStudyDate.EndAt -> {
                studyState.value!!.copy(endAt = date)
            }
        }

    }
}