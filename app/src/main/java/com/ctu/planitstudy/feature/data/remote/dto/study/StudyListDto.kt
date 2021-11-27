package com.ctu.planitstudy.feature.data.remote.dto.study

import com.google.gson.annotations.SerializedName

data class StudyListDto(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("studies")
    val studies: List<StudyDto>
)