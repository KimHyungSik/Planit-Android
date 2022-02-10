package com.ctu.planitstudy.feature.presentation.common.popup

import android.app.AlertDialog

data class PopupData(
    val title: String,
    val titleTextSize: Float = 18f,
    val message: String? = null,
    val buttonTitle: String? = null,
    val buttonFun: ((dialog: AlertDialog) -> Unit)? = null,
    val subButtonTitle: String? = null,
    val subButtonFun: ((dialog: AlertDialog) -> Unit)? = null
)
