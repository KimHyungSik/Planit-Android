package com.ctu.planitstudy.feature.data.remote.dto.Dday

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class DdayListDto(
    @SerializedName("ddays") val ddays: List<DdayDto>
)