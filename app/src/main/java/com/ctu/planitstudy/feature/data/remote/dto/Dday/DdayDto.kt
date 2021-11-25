package com.ctu.planitstudy.feature.data.remote.dto.Dday

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DdayDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("isRepresentative") val isRepresentative: Boolean,
    @SerializedName("endAt") val endAt: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("icon") val icon: String,
    val dDay: Int
) : Parcelable
