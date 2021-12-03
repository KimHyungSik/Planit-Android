package com.ctu.planitstudy.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val _loading = MutableLiveData<Boolean>(false)
    val loading : LiveData<Boolean> = _loading
}