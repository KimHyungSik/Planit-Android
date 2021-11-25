package com.ctu.planitstudy.feature.presentation.study

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.Weekday
import com.ctu.planitstudy.feature.domain.model.study.RepeatedStudy
import com.ctu.planitstudy.feature.domain.model.study.Study
import com.ctu.planitstudy.feature.domain.use_case.study.AddStudyUseCase
import com.ctu.planitstudy.feature.domain.use_case.study.StudyValidatedTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val addStudyUseCase: AddStudyUseCase,
    private val studyValidatedTitleUseCase: StudyValidatedTitleUseCase
) : ViewModel() {

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

    private val _studyDialogState = MutableLiveData<StudyDialogState>()
    val studyDialogState: LiveData<StudyDialogState> = _studyDialogState

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
        var newStudyStateWeek = studyState.value!!.week
        val week = Weekday.values()[weekCount]

        // 매일 에서 다른 요일 선택
        if (newStudyStateWeek.contains(Weekday.All) && weekCount != Weekday.All.ordinal)
            newStudyStateWeek.remove(Weekday.All)

        if (isCheck && !newStudyStateWeek.contains(week))
            newStudyStateWeek.add(week)
        else
            newStudyStateWeek.remove(week)

        newStudyStateWeek.sortBy { it.ordinal }

        if (newStudyStateWeek.contains(Weekday.All))
            _studyState.value = studyState.value!!.copy(
                week = arrayListOf(Weekday.All)
            )
        else
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

    private fun getRepeatedStudy(): RepeatedStudy {
        studyState.value!!.apply {
            return RepeatedStudy(
                this.title,
                DateConvter.textDateToDtoDate(this.startAt),
                DateConvter.textDateToDtoDate(this.endAt),
                getWeekList()
            )
        }
    }

    private fun getStudy(): Study {
        studyState.value!!.apply {
            return Study(
                this.title,
                DateConvter.textDateToDtoDate(this.singleAt)
            )
        }
    }

    fun getWeekList(): ArrayList<String> {
        val weeks = ArrayList<String>()
        if (studyState.value!!.week.contains(Weekday.All))
            studyState.value!!.activationWeek.forEach { weeks.add(it.weekEng) }
        else
            studyState.value!!.week.forEach { weeks.add(it.weekEng) }
        return weeks
    }

    fun studyConfirmed() {
        if (studyState.value!!.title.isEmpty()) {
            _studyDialogState.value = StudyDialogState(emptyTitleDialog = true)
            return
        }
        viewModelScope.launch {
            try {
                Log.d(TAG, "studyConfirmed: ${studyState.value!!.title}")
                studyValidatedTitleUseCase(studyState.value!!.title)
            } catch (e: HttpException) {
                _studyDialogState.value = StudyDialogState(validatedTitle = true)
                return@launch
            }
            try {
                Log.d(TAG, "studyConfirmed: ${studyState.value}")
                if (studyState.value!!.repeat)
                    addStudyUseCase(getRepeatedStudy())
                else {
                    addStudyUseCase(getStudy())
                }.onEach {
                    when (it) {
                        is Resource.Success -> {
                            _studyDialogState.value = StudyDialogState(addStudy = true)
                        }
                        is Resource.Loading -> {
                            Log.d(TAG, "studyConfirmed: loading")
                        }
                        is Resource.Error -> {
                            Log.d(TAG, "studyConfirmed: ${it.message}")
                        }
                    }
                }.launchIn(this)
            } catch (e: HttpException) {
            }
        }
    }
}
