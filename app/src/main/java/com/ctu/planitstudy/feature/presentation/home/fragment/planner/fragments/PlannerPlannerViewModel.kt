package com.ctu.planitstudy.feature.presentation.home.fragment.planner.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class PlannerPlannerViewModel : ViewModel() {

    private val _plannerState = MutableLiveData<PlannerPlannerState>(PlannerPlannerState(LocalDate.now()))
    val plannerState: LiveData<PlannerPlannerState> = _plannerState

    fun updatePlannerState(plannerState: PlannerPlannerState) {
        _plannerState.value = plannerState
    }
}
