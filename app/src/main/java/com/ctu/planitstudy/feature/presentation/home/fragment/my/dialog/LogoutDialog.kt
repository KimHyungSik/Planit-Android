package com.ctu.planitstudy.feature.presentation.home.fragment.my.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.databinding.DialogFragmentDeleteDdayBinding
import com.ctu.planitstudy.databinding.DialogFragmentLogoutBinding
import com.ctu.planitstudy.feature.presentation.dday.DdayViewModel
import com.ctu.planitstudy.feature.presentation.home.fragment.my.MyViewModel

class LogoutDialog : DialogFragment() {

    val TAG = "Representative - 로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private lateinit var binding: DialogFragmentLogoutBinding
    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentLogoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutCheckCancel.setOnClickListener {
            this.dismiss()
        }
        binding.logoutCheckConfirmed.setOnClickListener {
            viewModel.logoutFun(true)
            this.dismiss()
        }
    }
}
