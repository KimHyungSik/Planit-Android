package com.ctu.planitstudy.feature.presentation.home.fragment.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {

    val TAG = "HomeViewModel - 로그"

    init {
        Log.d(TAG, "Init: ${CashStudyApp.prefs.accessToken}")
        Log.d(TAG, "Init: ${CashStudyApp.prefs.refreshToken}")
        val jwt = JWT(CashStudyApp.prefs.accessToken!!)



    }
}