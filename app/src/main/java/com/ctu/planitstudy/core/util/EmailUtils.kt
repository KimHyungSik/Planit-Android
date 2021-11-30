package com.ctu.planitstudy.core.util

import android.content.Context
import android.content.Intent
import android.os.Build
import com.ctu.planitstudy.R

class EmailUtils {
    companion object {
        fun sendEmail(context: Context, title: String, receivers: Array<String>) {
            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_SUBJECT, title)
            email.putExtra(Intent.EXTRA_EMAIL, receivers)
            email.putExtra(Intent.EXTRA_TEXT, "앱 버전 (AppVersion):" + R.string.version + "\n기기명 (Device)" + android.os.Build.MODEL + ":\n안드로이드 OS (Android OS):" + Build.VERSION.SDK_INT + "\n내용 (Content):\n")
            email.type = "message/rfc822"
            context.startActivity(email)
        }
    }
}
