package com.ctu.planitstudy.feature.data.data_source.user

import android.content.Context
import com.ctu.planitstudy.feature.domain.model.User
import com.ctu.core.util.Resource
import io.reactivex.Completable
import io.reactivex.Single

interface OauthUserPolicy {
    fun login(context: Context) : Single<Resource<String>>
    fun logout() : Completable
    fun getUserInfo() : Single<Resource<User>>
}