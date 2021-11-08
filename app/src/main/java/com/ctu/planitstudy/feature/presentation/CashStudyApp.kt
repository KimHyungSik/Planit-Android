package com.ctu.planitstudy.feature.presentation

import android.app.Application
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.util.PreferencesManager
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CashStudyApp:Application() {
    companion object{
        lateinit var prefs:PreferencesManager
    }
    override fun onCreate() {
        prefs=PreferencesManager(applicationContext)
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }

}