package com.ctu.planitstudy.feature.presentation.terms_of_use.webview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.webkit.WebSettings
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.databinding.ActivityTermsOfUseBinding

class TermsOfUse : BaseBindingActivity<ActivityTermsOfUseBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityTermsOfUseBinding
        get() = ActivityTermsOfUseBinding::inflate

    @SuppressLint("SetJavaScriptEnabled")
    override fun setup() {
        
        val url = intent.getStringExtra("Url") ?: getString(R.string.terms_of_service)
        with(binding.webView) {
            settings.javaScriptEnabled = true
            settings.setSupportMultipleWindows(false)
            loadUrl(
                "https://docs.google.com/viewer?url=$url"
            )
        }
    }

}