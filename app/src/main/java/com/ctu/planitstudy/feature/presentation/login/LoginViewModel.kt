package com.ctu.planitstudy.feature.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.data.data_source.user.OauthType
import com.ctu.planitstudy.feature.data.data_source.user.UserManager
import com.ctu.planitstudy.feature.data.remote.dto.JsonConverter
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
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
) : BaseViewModel() {
    val TAG = "LoginViewModel - 로그"

    val loginState: MutableLiveData<LoginState> by lazy {
        MutableLiveData<LoginState>()
    }

    var disposables = CompositeDisposable()

    fun changeUserPolicy(oauthType: OauthType) {
        userManager.userPolicyChange(oauthType)
    }

    fun login(context: Context) {
        changeUserPolicy(OauthType.KakaoOauth)
        userManager.userLogin(context)
            .subscribe(
                { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            loginState.postValue(LoginState.Loading(true))
                            userManager.getUserInfo()
                                .subscribe(
                                    { it ->
                                        when (it) {
                                            is Resource.Success -> {
                                                userAuthUseCase.userLogin(LoginUser(it.data!!.userEmail))
                                                    .subscribeOn(Schedulers.computation())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .map { JsonConverter.jsonToLoginDto(it.asJsonObject) }
                                                    .subscribe(
                                                        {
                                                            CashStudyApp.prefs.accessToken = it.accessToken
                                                            CashStudyApp.prefs.refreshToken =
                                                                it.refreshToken
                                                            loginState.postValue(LoginState.Login(it.result))
                                                        },
                                                        {
                                                            Log.d(
                                                                TAG,
                                                                "login: userAuthUseCase : ${it.message}"
                                                            )
                                                        }
                                                    )
                                            }
                                            is Resource.Error -> {
                                                Log.e(TAG, "login: getUserInfo:${it.data}")
                                            }
                                        }
                                    },
                                    {
                                    }
                                )
                        }
                        is Resource.Error -> {
                            loginState.postValue(LoginState.Loading(false))
                            Log.d(TAG, "login: Error: ${resource.message}")
                        }
                    }
                },
                { error ->
                    Log.d(TAG, "login: error ${error.message}")
                }
            ).addTo(disposables)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
