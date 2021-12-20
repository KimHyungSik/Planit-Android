package com.ctu.planitstudy.core.base

sealed class LoadingState {
    object Dismiss : LoadingState()
    object Show : LoadingState()
    object ErrorDismiss : LoadingState()
}
