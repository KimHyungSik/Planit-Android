package com.ctu.planitstudy.feature.presentation.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.data.data_source.user.OauthType
import com.ctu.planitstudy.feature.data.data_source.user.UserManager
import com.ctu.planitstudy.feature.data.remote.dto.LoginDto
import com.ctu.planitstudy.feature.domain.model.user.LoginUser
import com.ctu.planitstudy.feature.domain.model.user.User
import com.ctu.planitstudy.feature.domain.use_case.user.UserAuthUseCase
import com.ctu.planitstudy.feature.presentation.CashStudyApp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import retrofit2.Response
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

    private fun changeUserPolicy(oauthType: OauthType) {
        userManager.userPolicyChange(oauthType)
    }

    fun kakaoLogin(context: Context) {
        login(context, OauthType.KakaoOauth)
    }

    private fun login(context: Context, oauthType: OauthType) {
        changeUserPolicy(oauthType)
        userManager.userLogin(context)
            .subscribe(
                { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            loginState.postValue(LoginState.Loading(true))
                            userManager.getUserInfo()
                                .subscribe(
                                    { it ->
                                        loginWithServer(it)
                                    },
                                    {}
                                )
                        }
                        is Resource.Error -> {
                            loginState.postValue(LoginState.Loading(false))
                        }
                    }
                },
                { error ->
                }
            ).addTo(disposables)
    }

    private fun loginWithServer(resource: Resource<User>) {
        when (resource) {
            is Resource.Success -> {
                viewModelScope.launch {
                    val login: Response<LoginDto> =
                        userAuthUseCase.userLogin(LoginUser(resource.data!!.userEmail))
                    if (login.isSuccessful) {
                        login.body()?.let {
                            if (it.result)
                                with(it) {
                                    CashStudyApp.prefs.accessToken =
                                        this.accessToken
                                    CashStudyApp.prefs.refreshToken =
                                        this.refreshToken
                                    loginState.postValue(
                                        LoginState.Login(
                                            this.result
                                        )
                                    )
                                }
                            else
                                loginState.postValue(
                                    LoginState.Login(
                                        it.result
                                    )
                                )
                        }
                    } else if (login.code() == 412) {
                        showAppUpdate()
                    }
                }
            }
            is Resource.Error -> {
            }
            else -> {
            }
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
