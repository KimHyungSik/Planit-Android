package com.ctu.planitstudy.feature.presentation.dialogs

import android.app.Activity
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
import com.ctu.planitstudy.databinding.DialogFragmentSubtitleBinding

class SingleTitleDoubleBtnDialog : DialogFragment() {

    val TAG = "Representative - 로그"

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDismiss(dialog: DialogInterface) {
        val parentFragment: Fragment? = parentFragment
        Log.d(TAG, "onDismiss: $parentFragment")
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
