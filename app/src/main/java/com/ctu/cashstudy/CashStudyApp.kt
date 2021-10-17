package com.ctu.cashstudy

import android.app.Application
import com.ctu.cashstudy.core.util.CoreData.KAKAO_API_KEY
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CashStudyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KAKAO_API_KEY)
    }

}