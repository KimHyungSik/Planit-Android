package com.ctu.planitstudy.feature.presentation.recycler.study

interface InStudyListRecycler {
    fun onClickedItem(position: Int)
    fun onClickedCheckbox(position: Int, check: Boolean)
}
