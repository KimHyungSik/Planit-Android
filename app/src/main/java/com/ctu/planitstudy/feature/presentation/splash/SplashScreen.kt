package com.ctu.planitstudy.feature.presentation.splash

import android.util.Base64.*
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.network.JWTRefreshTokenExpiration
import com.ctu.planitstudy.databinding.ActivitySplashScreenBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SplashScreen : BaseBindingActivity<ActivitySplashScreenBinding, SplashScreenViewModel>() {
    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding
        get() = ActivitySplashScreenBinding::inflate
    override val viewModel: SplashScreenViewModel by viewModels()

    override fun setup() {
        Completable.complete()
            .delay(1500, TimeUnit.MILLISECONDS)
            .subscribe {
                if (CashStudyApp.prefs.refreshToken != null)
                    if (CashStudyApp.prefs.refreshToken!!.isNotBlank())
                        if (!JWTRefreshTokenExpiration().invoke()) {
                            viewModel.getToken()
                        } else
                            moveIntentAllClear(Screens.LoginScreenSh.activity)
                    else
                        moveIntentAllClear(Screens.LoginScreenSh.activity)
                else
                    moveIntentAllClear(Screens.LoginScreenSh.activity)
            }
            .isDisposed

        viewModel.tokenChek.observe(this, {
            if (it)
                moveIntentAllClear(Screens.HomeScreenSh.activity)
            else
                moveIntentAllClear(Screens.LoginScreenSh.activity)
        })
    }
}
