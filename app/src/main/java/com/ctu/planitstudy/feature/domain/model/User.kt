package com.ctu.planitstudy.feature.domain.model

import java.io.Serializable

data class User (
    val userId : String,
    val userNickname : String,
    val userEmail : String
) :Serializable