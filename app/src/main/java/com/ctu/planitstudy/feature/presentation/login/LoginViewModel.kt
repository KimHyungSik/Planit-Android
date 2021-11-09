package com.ctu.planitstudy.feature.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctu.planitstudy.feature.data.data_source.user.OauthType
import com.ctu.planitstudy.feature.data.data_source.user.UserManager
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.CoreData
import com.ctu.planitstudy.core.util.PreferencesManager
import com.ctu.planitstudy.feature.data.remote.dto.JsonConverter
import com.ctu.planitstudy.feature.domain.model.LoginUser
import com.ctu.planitstudy.feature.domain.use_case.user.UserAuthUseCase
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userManager: UserManager,
    private val userAuthUseCase: UserAuthUseCase
) : ViewModel()
{
    val TAG = "LoginViewModel - 로그"

    val loginState : MutableLiveData<LoginState> by lazy{
        MutableLiveData<LoginState>()
    }

    var disposables = CompositeDisposable()

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

        userManager.userLogin(context).
        subscribe(
            { resource ->
                Log.d(TAG, "login: subscribe")
                when (resource) {
                    is Resource.Success -> {
                        loginState.postValue(LoginState.Loading(true))
                        userManager.getUserInfo()
                            .subscribe({ it ->
                                Log.d(TAG, "login: subscribe :$it")
                                when (it) {
                                    is Resource.Success -> {
                                        Log.d(TAG, "login: user email ${it.data!!.userEmail}")
                                        userAuthUseCase.userLogin(LoginUser(it.data!!.userEmail))
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .map { JsonConverter.jsonToLoginDto(it.asJsonObject) }
                                            .subscribe({
                                                Log.d(TAG, "login: $it")
                                                loginState.postValue(LoginState.Login(it.result))
                                                CashStudyApp.prefs.accessToken = it.accessToken
                                                CashStudyApp.prefs.refreshToken = it.refreshToken
                                            }, {
                                                Log.e(TAG, "login: userLoginUseCase :$it")
                                            })

                                    }
                                    is Resource.Error -> {
                                        Log.e(TAG, "login: getUserInfo:${it.data}")
                                    }
                                }
                            }, {
                            })
                        Log.i(TAG, "로그인 성공 ${resource.data}")
                    }
                    is Resource.Error -> {
                        loginState.postValue(LoginState.Loading(false))
                        Log.e(TAG, "로그인 실패 ${resource.message}")
                    }
                }
            },
            { error ->
                Log.d(TAG, "login error: ${error.localizedMessage}")
            }).addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}