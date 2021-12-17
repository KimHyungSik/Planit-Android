package com.ctu.planitstudy.feature.data.remote.dto.reward

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RewardDto(
    @SerializedName("planetPass")
    val planetPass: Int,
    @SerializedName("point")
    val point: Int,
    @SerializedName("star")
    val star: Int
) : Parcelable
