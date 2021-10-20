package com.ctu.cashstudy.feature.data.data_source.user

import android.content.Context
import android.util.Log
import com.ctu.cashstudy.feature.data.data_source.user.componets.KakaoOauthImp
import com.ctu.core.util.Resource
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

    fun userLogin(context : Context) : Resource<String> = userPolicy!!.login(context)

    fun userLogout(){
        userPolicy!!.logout()
    }

    fun userGetId(): String {
        val result = userPolicy!!.getUserId()
        return when(result){
            is Resource.Error -> result.message!!
            is Resource.Success -> result.data!!
            is Resource.Loading -> "loding"
        }
    }

}