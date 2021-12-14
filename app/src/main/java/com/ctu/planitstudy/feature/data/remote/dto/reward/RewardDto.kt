package com.ctu.planitstudy.feature.data.remote.dto.reward

import com.google.gson.annotations.SerializedName

data class RewardDto(
    @SerializedName("planetPass")
    val planetPass: Int,
    @SerializedName("point")
    val point: Int,
    @SerializedName("star")
    val star: Int
)
