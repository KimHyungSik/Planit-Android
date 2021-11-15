package com.ctu.planitstudy.feature.presentation

import android.app.Application
import android.content.Context
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.util.PreferencesManager
import com.kakao.sdk.common.KakaoSdk
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CashStudyApp : Application() {
    companion object {
        lateinit var prefs: PreferencesManager
        lateinit var instance: CashStudyApp
            private set
    }

    override fun onCreate() {
        prefs = PreferencesManager(applicationContext)
        super.onCreate()
        instance = this
        KakaoSdk.init(this, getString(R.string.kakao_app_key))

        AppCenter.start(
            this, "22a02af7-2690-4035-8d51-3bb28e1f4e64",
            Analytics::class.java, Crashes::class.java
        )
    }

    fun context(): Context = applicationContext

}