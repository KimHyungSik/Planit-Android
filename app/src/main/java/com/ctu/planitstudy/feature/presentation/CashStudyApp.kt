package com.ctu.planitstudy.feature.presentation

import android.app.Application
import android.content.Context
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.util.PreferencesManager
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
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
        MobileAds.initialize(this) {}

        AppCenter.start(
            this, "c5544aad-6922-44df-9ce2-27f6911cebbb",
            Analytics::class.java, Crashes::class.java
        )
    }
    fun context(): Context = applicationContext
}
