package com.ctu.planitstudy.feature.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.databinding.DialogFragmentLoadingBinding
import com.ctu.planitstudy.databinding.DialogFragmentLogoutBinding
import com.ctu.planitstudy.databinding.DialogFragmentReadyBinding
import com.ctu.planitstudy.feature.presentation.dday.DdayViewModel

class LoadingDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DialogFragmentLoadingBinding.inflate(layoutInflater).root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
