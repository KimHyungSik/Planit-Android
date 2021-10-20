package com.ctu.cashstudy.feature.presentation

import android.app.Application
import com.ctu.cashstudy.R
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CashStudyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }

}