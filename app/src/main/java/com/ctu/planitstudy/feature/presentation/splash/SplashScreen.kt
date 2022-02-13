package com.ctu.planitstudy.feature.presentation.splash

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.net.Uri
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.ctu.planitstudy.R
import com.ctu.planitstudy.core.base.BaseBindingActivity
import com.ctu.planitstudy.core.util.network.JWTRefreshTokenExpiration
import com.ctu.planitstudy.databinding.ActivitySplashScreenBinding
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import com.ctu.planitstudy.feature.presentation.common.popup.PopupData
import com.ctu.planitstudy.feature.presentation.common.popup.PopupHelper
import com.ctu.planitstudy.feature.presentation.dialogs.SubTitleCheckDialog
import com.ctu.planitstudy.feature.presentation.util.Screens
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class SplashScreen :
    BaseBindingActivity<ActivitySplashScreenBinding, SplashScreenViewModel>(),
    DialogInterface.OnDismissListener {

    val TAG = "SplashScreen - 로그"

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding
        get() = ActivitySplashScreenBinding::inflate
    override val viewModel: SplashScreenViewModel by viewModels()

    val REQUEST_CODE_UPDATE = 1
    private lateinit var cm2: ConnectivityManager

    private val networkCheckJob = Job()
    private var checkNetWork = false

    @DelicateCoroutinesApi
    private val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            checkNetWork = true
            // 네트워크가 연결될 때 호출됩니다.
            CoroutineScope(Dispatchers.Main + mainJob).launch {
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
        }
    }

    @DelicateCoroutinesApi
    override fun setup() {
        cm2 = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        cm2.registerNetworkCallback(
            builder.build(),
            networkCallBack
        )

        showNetworkErrorDialog()

        viewModel.tokenChek.observe(
            this,
            {
                if (it)
                    moveIntentAllClear(Screens.HomeScreenSh.activity)
                else
                    moveIntentAllClear(Screens.LoginScreenSh.activity)
            }
        )
    }

    private fun showNetworkErrorDialog() {
        if(checkNetWork) return
        CoroutineScope(Dispatchers.Main + networkCheckJob + mainJob).launch {
            delay(1500)
            CoroutineScope(Dispatchers.Main + networkCheckJob + mainJob).launch {
                delay(1500)
                PopupHelper.createPopUp(
                    context = this@SplashScreen,
                    PopupData(
                        title = getString(R.string.network_error_title),
                        titleTextSize = 18f,
                        message = getString(R.string.network_error_sub_title),
                        buttonTitle = getString(R.string.retry),
                        buttonFun = {
                            showNetworkErrorDialog()
                            it.dismiss()
                        }
                    )
                ).show()
            }
        }
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

    override fun onDismiss(dialog: DialogInterface?) {
        showNetworkErrorDialog()
    }

    override fun onDestroy() {
        networkCheckJob.cancel()
        mainJob.cancel()
        cm2 = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm2.unregisterNetworkCallback(networkCallBack)
        super.onDestroy()
    }


}
