package com.ctu.planitstudy.feature.data.data_source.user

import android.content.Context
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.data_source.user.componets.KakaoOauthImp
import com.ctu.planitstudy.feature.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

class UserManager(
    private val kakaoOauth: KakaoOauthImp
) {
    val TAG = "UserManager - 로그"
    private var userPolicy: OauthUserPolicy? = null

    fun userPolicyChange(oauthType: OauthType) {
        when (oauthType) {
            is OauthType.KakaoOauth -> userPolicy = kakaoOauth
        }
    }

    fun userLogin(context: Context): Single<Resource<String>> = userPolicy!!.login(context)

    fun userLogout(): Completable = userPolicy!!.logout()

    fun getUserInfo(): Single<Resource<User>> = userPolicy!!.getUserInfo()
}
