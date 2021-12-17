package com.ctu.planitstudy.feature.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.domain.repository.AuthRepository
import com.ctu.planitstudy.feature.domain.use_case.auth.JwtTokenRefreshUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val jwtTokenRefreshUseCase: JwtTokenRefreshUseCase
) : BaseViewModel() {

    private val _tokenChek = MutableLiveData<Boolean>()
    val tokenChek : LiveData<Boolean> = _tokenChek


    fun getToken() {
        jwtTokenRefreshUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _tokenChek.value = true
                    loadingDismiss()
                }
                is Resource.Error -> {
                    _tokenChek.value = false
                    loadingDismiss()
                }
                is Resource.Loading -> {
                    loadingShow()
                }
            }
        }.launchIn(viewModelScope)
    }
}
