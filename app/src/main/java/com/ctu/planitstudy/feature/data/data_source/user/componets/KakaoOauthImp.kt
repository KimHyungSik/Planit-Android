package com.ctu.planitstudy.feature.data.data_source.user.componets

import android.content.Context
import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.data_source.user.OauthUserPolicy
import com.ctu.planitstudy.feature.domain.model.user.User
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.rx
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class KakaoOauthImp() : OauthUserPolicy {

    val TAG = "KakaoOauthImp - 로그"

    override fun login(context: Context): Single<Resource<String>> =
        UserApiClient.run {
            Single.just(instance.isKakaoTalkLoginAvailable(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { available ->
                    if (available) rx.loginWithKakaoTalk(context)
                    else rx.loginWithKakaoAccount(context)
                }
                .map { token ->
                    if (token != null) Resource.Success<String>("success")
                    else Resource.Error<String>(message = "error", null)
                }
        }

    override fun logout(): Completable =
        UserApiClient.rx.logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getUserInfo(): Single<Resource<User>> =
        Single.just(AuthApiClient.instance.hasToken())
            .flatMap { available ->
                UserApiClient.rx.me()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { user ->
                Resource.Success(
                    User(
                        user.id.toString(),
                        user.kakaoAccount?.profile?.nickname ?: "",
                        user.kakaoAccount?.email!!
                    )
                )
            }
}
