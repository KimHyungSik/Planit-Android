package com.ctu.planitstudy.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val _loading = MutableLiveData<Boolean?>(null)
    val loading: LiveData<Boolean?> = _loading

    open fun loadingShow() {
        if (loading.value == null)
            _loading.value = true
        if (!loading.value!!)
            _loading.value = true
    }

    open fun loadingDismiss() {
        if (loading.value == null)
            _loading.value = false
        if (loading.value!!)
            _loading.value = false
    }
}
