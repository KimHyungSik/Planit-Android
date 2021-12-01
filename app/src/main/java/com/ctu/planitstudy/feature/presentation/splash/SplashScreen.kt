package com.ctu.planitstudy.feature.presentation.splash

import android.os.Build
import android.util.Base64.*
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.network.JWTRefreshTokenExpiration
import com.ctu.planitstudy.databinding.ActivitySplashScreenBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.util.Screens
import io.reactivex.Completable
import java.util.*
import java.util.concurrent.TimeUnit

class SplashScreen : BaseBindingActivity<ActivitySplashScreenBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding
        get() = ActivitySplashScreenBinding::inflate

    @RequiresApi(Build.VERSION_CODES.P)
    override fun setup() {
        Completable.complete()
            .delay(1500, TimeUnit.MILLISECONDS)
            .subscribe {
                if (CashStudyApp.prefs.refreshToken != null)
                    if (CashStudyApp.prefs.refreshToken!!.isNotBlank())
                        if (!JWTRefreshTokenExpiration().invoke())
                            moveIntentAllClear(Screens.HomeScreenSh.activity)
                        else
                            moveIntentAllClear(Screens.LoginScreenSh.activity)
                    else
                        moveIntentAllClear(Screens.LoginScreenSh.activity)
                else
                    moveIntentAllClear(Screens.LoginScreenSh.activity)
            }
            .isDisposed
    }
}
