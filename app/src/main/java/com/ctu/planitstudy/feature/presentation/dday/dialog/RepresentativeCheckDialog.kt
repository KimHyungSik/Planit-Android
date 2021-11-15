package com.ctu.planitstudy.feature.presentation.dday.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ctu.planitstudy.databinding.DialogFragmentRepresentativeBinding
import com.ctu.planitstudy.feature.presentation.dday.DdayViewModel

class RepresentativeCheckDialog : DialogFragment() {

    val TAG = "Representative - 로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private lateinit var binding: DialogFragmentRepresentativeBinding
    private val viewModel: DdayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentRepresentativeBinding.inflate(inflater,container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dDayRepresentativeCheckCancel.setOnClickListener {
            viewModel.onSwitchChanged(false)
            this.dismiss()
        }
        binding.dDayRepresentativeCheckConfirmed.setOnClickListener {
            this.dismiss()
        }
    }
}