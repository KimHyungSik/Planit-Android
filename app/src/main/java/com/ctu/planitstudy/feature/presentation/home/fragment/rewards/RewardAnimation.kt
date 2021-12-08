package com.ctu.planitstudy.feature.presentation.home.fragment.rewards

import android.view.animation.TranslateAnimation

object RewardAnimation {

    fun getAniLeft(): TranslateAnimation {
        val aniLeft = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_PARENT,
            0f,
            TranslateAnimation.RELATIVE_TO_PARENT,
            -1f,
            TranslateAnimation.RELATIVE_TO_PARENT,
            0f,
            TranslateAnimation.RELATIVE_TO_PARENT,
            0f
        )
        aniLeft.fillAfter = true
        aniLeft.duration = 100000
        return aniLeft
    }
}
