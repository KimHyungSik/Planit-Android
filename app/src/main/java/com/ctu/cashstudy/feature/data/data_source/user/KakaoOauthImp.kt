package com.ctu.cashstudy.feature.data.data_source.user

import com.kakao.sdk.user.UserApiClient
import com.ctu.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KakaoOauthImp: OauthUserPolicy {
    override fun login() {
    }

    override fun getUserId(): Flow<Resource<String>> = flow{
        var result: Resource<String> = Resource.Error<String>(message = "Failed To Start")

        emit(Resource.Loading<String>())
        UserApiClient.instance.me{ user, error ->
            if (error != null) {
                result = Resource.Error<String>(message = error.localizedMessage, null)
            }
            else if (user != null) {
                result = Resource.Success<String> (user.id.toString())
            }
        }
        emit(result)
    }

}