package com.ctu.cashstudy.feature.data.data_source.user

import com.ctu.core.util.Resource
import kotlinx.coroutines.flow.Flow


interface OauthUserPolicy {
    fun login()
    fun getUserId() : Flow<Resource<String>>
}