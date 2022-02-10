package com.ctu.planitstudy.feature.presentation.common.popup

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ctu.planitstudy.R
import com.ctu.planitstudy.databinding.DialogMessageAlertBinding

class PopupHelper {
    companion object {

        fun createPopUp(context: Context, popupData: PopupData) : AlertDialog {
            val dialog = AlertDialog.Builder(context)
                .create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogView = createPopUpView(context, popupData, dialog)
            dialog.setView(dialogView)
            return dialog
        }

        private fun createPopUpView(context: Context, popupData: PopupData, dialog : AlertDialog) : View {
            val layoutInflater = LayoutInflater.from(context)
            val dialogView: DialogMessageAlertBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.dialog_message_alert, null, false)
            dialogView.popupData = popupData
            dialogView.mDialog = dialog
            return dialogView.root
        }

    }
}