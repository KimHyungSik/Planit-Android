package com.ctu.planitstudy.feature.data.remote.dto.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInformationDto(
    @SerializedName("birth")
    val birth: String,
    @SerializedName("birth")
    val category: String,
    @SerializedName("birth")
    val email: String,
    @SerializedName("birth")
    val id: Int,
    @SerializedName("birth")
    val name: String,
    @SerializedName("birth")
    val nickname: String,
    @SerializedName("birth")
    val sex: String
) : Parcelable
