package com.ctu.planitstudy.feature.data.data_source.user

import com.ctu.planitstudy.feature.data.data_source.user.componets.KakaoOauthImp

sealed class OauthType(val oauthUserPolicy: OauthUserPolicy) {
    object KakaoOauth : OauthType(KakaoOauthImp())
}
