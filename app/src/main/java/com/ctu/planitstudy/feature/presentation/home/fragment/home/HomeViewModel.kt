package com.ctu.planitstudy.feature.presentation.home.fragment.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.ctu.planitstudy.feature.domain.use_case.dday.GetDdayListUseCase
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ddayListUseCase: GetDdayListUseCase,
    private val ddayRepository: DdayRepository
) : ViewModel() {

    val TAG = "HomeViewModel - 로그"

    init {
        Log.d(TAG, "Init: ${CashStudyApp.prefs.accessToken}")
        Log.d(TAG, "Init: ${CashStudyApp.prefs.refreshToken}")
        val jwt = JWT(CashStudyApp.prefs.accessToken!!)
        ddayListUseCase().onEach {
            when(it){
                is Resource.Success -> {
                    Log.i(TAG, "Init useCase: ${it.data}")
                }
                is Resource.Loading ->{
                    Log.d(TAG, "Init useCase: loading")
                }
                is Resource.Error -> {
                    Log.e(TAG, "Init useCase: ${it.message}")
                }
            }
        }.launchIn(viewModelScope)
    }
}