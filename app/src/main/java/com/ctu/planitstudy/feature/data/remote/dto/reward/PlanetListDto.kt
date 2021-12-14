package com.ctu.planitstudy.feature.data.remote.dto.reward

import com.google.gson.annotations.SerializedName

data class PlanetListDto(
    @SerializedName("planets")
    val planets: List<PlanetDto> = mutableListOf()
)
