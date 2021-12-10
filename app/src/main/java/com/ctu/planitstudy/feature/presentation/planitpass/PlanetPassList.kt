package com.ctu.planitstudy.feature.presentation.planitpass

data class PlanetPassList(
    val PlanetPassList: List<PlanetPass> = emptyList()
)

data class PlanetPass(
    val id : Int,
    val name: String,
    val description: String
)