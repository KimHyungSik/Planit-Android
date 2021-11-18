package com.ctu.planitstudy.feature.presentation.study

data class StudyDialogState(
    val addStudy : Boolean = false,
    val deleteDialog: Boolean = false,
    val emptyTitleDialog: Boolean = false,
    val validatedTitle: Boolean = false
)
