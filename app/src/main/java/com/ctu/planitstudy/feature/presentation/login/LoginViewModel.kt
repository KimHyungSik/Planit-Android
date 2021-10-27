package com.ctu.planitstudy.feature.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctu.planitstudy.feature.data.data_source.user.OauthType
import com.ctu.planitstudy.feature.data.data_source.user.UserManager
import com.ctu.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
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

    var disposables = CompositeDisposable()

    fun getUserId() {
        userManager.userGetId()
            .subscribe({ resource ->
                Log.d(TAG, "getUserId: ${resource.data.toString()}")
            }, {
                Log.d(TAG, "getUserId: 유저 정보 조회 실패")
            }).isDisposed
    }

    fun changeUserPolicy(oauthType: OauthType){
        userManager.userPolicyChange(oauthType)
    }

    fun logout(){
        userManager.userLogout()
            .subscribe({
                Log.d(TAG, "logout: 로그아웃 성공")
            }, {
                Log.d(TAG, "logout: 로그아웃 실패")
            }).isDisposed
    }

    fun login(context: Context){
        changeUserPolicy(OauthType.KakaoOauth)
        Log.d(TAG, "login: ")
        userManager.userLogin(context).
        subscribe(
            { resource ->
                Log.d(TAG, "login: subscribe")
                when (resource) {
                    is Resource.Success -> {
                        loginState.postValue(LoginState(isLogin = true))
                        Log.i(TAG, "로그인 성공 ${resource.data}")
                    }
                    is Resource.Error -> {
                        loginState.postValue(LoginState(error = resource.message!!))
                        Log.e(TAG, "로그인 실패 ${resource.message}")
                    }
                }
            },
            { error ->
                Log.d(TAG, "login: $error")
            }).addTo(disposables)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        disposables.clear()
        super.onCleared()
    }

}