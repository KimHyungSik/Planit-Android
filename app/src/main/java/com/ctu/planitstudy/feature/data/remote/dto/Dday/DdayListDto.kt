package com.ctu.planitstudy.feature.data.remote.dto.Dday

import com.google.gson.annotations.SerializedName

data class DdayListDto(
    @SerializedName("ddays") val ddays: List<DdayDto>
)
