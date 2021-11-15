package com.ctu.planitstudy.feature.presentation.dday

import androidx.lifecycle.ViewModel
import com.ctu.planitstudy.feature.domain.use_case.dday.DdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DdayViewModel @Inject constructor(
    private val ddayUseCase: DdayUseCase
) : ViewModel() {

}