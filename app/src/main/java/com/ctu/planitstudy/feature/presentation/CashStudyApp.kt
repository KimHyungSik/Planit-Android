package com.ctu.planitstudy.feature.presentation

import android.app.Application
import android.content.Context
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.util.PreferencesManager
import com.google.android.gms.ads.MobileAds
import com.kakao.sdk.common.KakaoSdk
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
    }
    fun context(): Context = applicationContext
}
