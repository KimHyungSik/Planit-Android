package com.ctu.planitstudy.feature.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.ctu.planitstudy.databinding.DialogFragmentLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DialogFragmentLoadingBinding.inflate(layoutInflater).root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
