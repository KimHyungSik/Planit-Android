package com.ctu.planitstudy.feature.data.remote.dto.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInformationDto(
    @SerializedName("birth")
    val birth: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("sex")
    val sex: String
) : Parcelable
