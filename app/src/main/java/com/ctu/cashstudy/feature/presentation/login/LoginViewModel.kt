package com.ctu.cashstudy.feature.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctu.cashstudy.feature.data.data_source.user.componets.KakaoOauthImp
import com.ctu.cashstudy.feature.data.data_source.user.OauthType
import com.ctu.cashstudy.feature.data.data_source.user.OauthUserPolicy
import com.ctu.cashstudy.feature.data.data_source.user.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel()
{
    val TAG = "LoginViewModel - 로그"

    val loginState : MutableLiveData<LoginState> by lazy{
        MutableLiveData<LoginState>()
    }

    fun getUserId() = viewModelScope.launch {  userManager.userGetId() }

    fun changeUserPolicy(oauthType: OauthType){
        userManager.userPolicyChange(oauthType)
    }

    fun logout(){
        userManager.userLogout()
    }

    fun login(context: Context){
        changeUserPolicy(OauthType.KakaoOauth)
        val response = userManager.userLogin(context)
        Log.d(TAG, "login: ${response.data ?: "data null"}")
        Log.d(TAG, "login: ${response.message ?: "message null"}")
    }

}