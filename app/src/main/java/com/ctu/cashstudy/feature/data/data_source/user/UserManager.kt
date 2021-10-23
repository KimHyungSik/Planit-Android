package com.ctu.cashstudy.feature.data.data_source.user

import android.content.Context
import android.util.Log
import com.ctu.cashstudy.feature.data.data_source.user.componets.KakaoOauthImp
import com.ctu.cashstudy.feature.domain.model.User
import com.ctu.core.util.Resource
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

class UserManager (
        private val kakaoOauth : KakaoOauthImp
){
    val TAG = "UserManager - 로그"
    private var userPolicy: OauthUserPolicy? = null

    fun userPolicyChange(oauthType: OauthType){
        when(oauthType){
            is OauthType.KakaoOauth -> userPolicy = kakaoOauth
        }
    }

    fun userLogin(context : Context) : Single<Resource<String>> = userPolicy!!.login(context)

    fun userLogout() : Completable = userPolicy!!.logout()

    fun userGetId(): Single<Resource<User>> = userPolicy!!.getUserInfo()


}