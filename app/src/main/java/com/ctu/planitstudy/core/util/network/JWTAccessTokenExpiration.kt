package com.ctu.planitstudy.core.util.network

import com.auth0.android.jwt.JWT
import com.ctu.planitstudy.feature.presentation.CashStudyApp

class JWTAccessTokenExpiration {
    // 참이 라면 토큰 만료
    operator fun invoke() : Boolean =
    JWT(CashStudyApp.prefs.accessToken!!).expiresAt!!.time <= System.currentTimeMillis()
}