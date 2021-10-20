package com.ctu.cashstudy.feature.data.data_source.user

import android.content.Context
import com.ctu.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface OauthUserPolicy {
    fun login(context: Context) :  Resource<String>
    fun logout()
    fun getUserId() : Resource<String>
}