package com.ctu.planitstudy.feature.presentation.splash

import android.util.Base64.*
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import coil.request.Disposable
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.network.JWTRefreshTokenExpiration
import com.ctu.planitstudy.databinding.ActivitySplashScreenBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class SplashScreen : BaseBindingActivity<ActivitySplashScreenBinding, SplashScreenViewModel>() {

    val TAG = "SplashScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding
        get() = ActivitySplashScreenBinding::inflate
    override val viewModel: SplashScreenViewModel by viewModels()

    val REQUEST_CODE_UPDATE = 7

    override fun setup() {
        val appUpdateManager = AppUpdateManagerFactory.create(CashStudyApp.instance)
        CoroutineScope(Dispatchers.Main).launch {
            val appUpdateInfo = appUpdateManager.appUpdateInfo.await()
            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    //업데이트 가능한 상태
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this@SplashScreen,
                        REQUEST_CODE_UPDATE
                    )

                }
            }
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
        viewModel.tokenChek.observe(this, {
            if (it)
                moveIntentAllClear(Screens.HomeScreenSh.activity)
            else
                moveIntentAllClear(Screens.LoginScreenSh.activity)
        })
    }

    suspend fun Task<AppUpdateInfo>.await(): AppUpdateInfo {
        return suspendCoroutine { continuation ->
            addOnCompleteListener { result ->

                if (result.isSuccessful) {
                    continuation.resume(result.result)
                } else {
                    continuation.resumeWithException(result.exception)
                }
            }
        }
    }
}
