package com.ctu.planitstudy.feature.data.remote.dto.reward

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanetDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("planetId")
    val planetId: Int
) : Parcelable
