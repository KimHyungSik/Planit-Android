package com.ctu.planitstudy.feature.presentation.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.ctu.planitstudy.databinding.DialogFragmentSingleTitleDoubleButtonBinding
import com.ctu.planitstudy.feature.presentation.dialogs.DialogText.LEFT_BTN_TEXT
import com.ctu.planitstudy.feature.presentation.dialogs.DialogText.RIGHT_BTN_TEXT
import com.ctu.planitstudy.feature.presentation.dialogs.DialogText.TITLE

class SingleTitleDoubleBtnDialog : DialogFragment() {

    val TAG = "SingleTitleDoubleBtnDialog - 로그"

    private var callback: DialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    private lateinit var binding: DialogFragmentSingleTitleDoubleButtonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentSingleTitleDoubleButtonBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        if (parentFragment is DialogListener) {
            callback = parentFragment as DialogListener
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            binding.dialogSingleTitleDoubleBtnText.text = requireArguments().getString(TITLE, "")
            binding.singleTitleDoubleBtnRightText.text = requireArguments().getString(
                RIGHT_BTN_TEXT,
                "취소"
            )
            binding.singleTitleDoubleBtnLeftText.text = requireArguments().getString(
                LEFT_BTN_TEXT,
                "멈춤"
            )
        }

        binding.dialogSingleTitleDoubleBtnCheckConfirmed.setOnClickListener {
            dismiss()
            callback?.onRightBtn()
        }
        binding.dialogSingleTitleDoubleBtnCheckCancel.setOnClickListener {
            dismiss()
            callback?.onLeftBtn()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        val parentFragment: Fragment? = parentFragment
        if (parentFragment is DialogInterface.OnDismissListener) {
            (parentFragment as DialogInterface.OnDismissListener?)!!.onDismiss(dialog)
        }

        val parentActivity: Activity? = activity
        if (parentActivity is DialogInterface.OnDismissListener) {
            (parentActivity as DialogInterface.OnDismissListener?)!!.onDismiss(dialog)
        }

        super.onDismiss(dialog)
    }
}
