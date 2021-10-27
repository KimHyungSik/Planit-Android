package com.ctu.planitstudy.feature.data.data_source.user

sealed class OauthType(val oauthType: String){
    object KakaoOauth: OauthType("Kakao Oauth")
}
