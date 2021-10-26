package com.ctu.cashstudy.feature.presentation.sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor()
    : ViewModel() {
    val TAG = "SignUpViewModel - 로그"

    private val _livedata = MutableLiveData<SignUpState>()
    val liveData : LiveData<SignUpState> = _livedata

    init {
        _livedata.value = SignUpState()
    }

    fun updateSignState(state : SignUpState){
        Log.d(TAG, "updateSignState: $state")
        _livedata.value = state
    }
}