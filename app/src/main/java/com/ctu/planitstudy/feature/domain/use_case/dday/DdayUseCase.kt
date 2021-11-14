package com.ctu.planitstudy.feature.domain.use_case.dday

data class DdayUseCase(
    val addDayUseCase: AddDdayUseCase,
    val deleteDdayUseCase: DeleteDdayUseCase,
    val getDdayListUseCase: GetDdayListUseCase,
    val modifiedDdayUseCase: ModifiedDdayUseCase
    )