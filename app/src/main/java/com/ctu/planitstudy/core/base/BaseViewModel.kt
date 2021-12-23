package com.ctu.planitstudy.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<LoadingState?>(null)
    val loading: LiveData<LoadingState?> = _loading

    open fun loadingShow() {
        if (loading.value == null)
            _loading.value = LoadingState.Show
        if (loading.value != LoadingState.Show)
            _loading.value = LoadingState.Show
    }

    open fun loadingDismiss() {
        if (loading.value == null)
            _loading.value = LoadingState.Dismiss
        if (loading.value == LoadingState.Show)
            _loading.value = LoadingState.Dismiss
    }

    open fun loadingErrorDismiss() {
        if (loading.value == null)
            _loading.value = LoadingState.ErrorDismiss
        if (loading.value == LoadingState.Show)
            _loading.value = LoadingState.ErrorDismiss
    }
}
