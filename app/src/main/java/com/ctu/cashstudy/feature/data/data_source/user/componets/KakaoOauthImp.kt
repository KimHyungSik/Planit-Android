package com.ctu.cashstudy.feature.data.data_source.user.componets

import android.content.Context
import android.util.Log
import com.ctu.cashstudy.feature.data.data_source.user.OauthUserPolicy
import com.ctu.core.util.Resource
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class KakaoOauthImp() : OauthUserPolicy {

    val TAG = "KakaoOauthImp - 로그"

    override fun login(context: Context): Resource<String> {
        var result: Resource<String> = Resource.Loading("login loading")

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                result = Resource.Error(message = error.localizedMessage!!)
                Log.d(TAG, "error kakaoLogin: $error")
            } else if (token != null) {
                result = Resource.Success(token.accessToken)
                Log.d(TAG, "success kakaoLogin: $token")
            }
        }

        UserApiClient.instance.run {
            // 카카오톡 설치 여부 확인
            if (isKakaoTalkLoginAvailable(context)) {
                // 설치 확인 시 카카오톡 앱 로그인 진행
                loginWithKakaoTalk(context, callback = callback)
            } else {
                // 미 설치 시 웹 브라우저로 설치 진행
                loginWithKakaoAccount(context, callback = callback)
            }
        }


        return result
    }

    override fun logout(){
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            } else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    override fun getUserId(): Resource<String> {
        var result: Resource<String> = Resource.Error(message = "Failed To Start get UserId")

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                result = Resource.Error(message = error.localizedMessage!!, null)
            } else if (user != null) {
                result = Resource.Success(user.id.toString())
            }
        }
        return result
    }

}