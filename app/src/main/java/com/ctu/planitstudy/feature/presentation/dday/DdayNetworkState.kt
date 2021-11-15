package com.ctu.planitstudy.feature.presentation.dday

data class DdayNetworkState(
    val deleteDay : Boolean = false,
    val addDday : Boolean = false,
    val modifiedDay : Boolean = false,
    val loading : Boolean = false
)
