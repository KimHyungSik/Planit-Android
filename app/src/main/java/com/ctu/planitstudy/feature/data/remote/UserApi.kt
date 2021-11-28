package com.ctu.planitstudy.feature.data.remote

import retrofit2.http.GET

interface UserApi {

    @GET("/v1/user")
    fun getUser()
}