package com.ctu.planitstudy.feature.presentation.study

sealed class KindStudyDate{
    object SingleDate : KindStudyDate()
    object StartAt: KindStudyDate()
    object EndAt: KindStudyDate()
}
