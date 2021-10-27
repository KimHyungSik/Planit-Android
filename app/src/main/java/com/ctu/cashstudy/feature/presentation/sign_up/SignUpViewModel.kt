package com.ctu.cashstudy.feature.presentation.sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctu.cashstudy.feature.presentation.sign_up.fragment.SignUpFragments
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor()
    : ViewModel() {
    val TAG = "SignUpViewModel - 로그"

    private val _livedata = MutableLiveData<SignUpState>(SignUpState())
    val liveData : LiveData<SignUpState> = _livedata

    private val _activityState = MutableLiveData<Boolean>(false)
    val activityState : LiveData<Boolean> = _activityState

    private val _signUpFragments = MutableLiveData<SignUpFragments>(SignUpFragments.Name)
    val signUpFragments : LiveData<SignUpFragments> = _signUpFragments

    var fragmentPage = 0

    val fragmentsList = listOf<SignUpFragments>(
        SignUpFragments.Name,
        SignUpFragments.Gender,
        SignUpFragments.DateOfBirth,
        SignUpFragments.Category,
    )

    init {
        liveData.observeForever {  signUpState ->
            _activityState.value = (!signUpState.nickname.isNullOrEmpty() && !signUpState.name.isNullOrEmpty()) || !signUpState.gender.isNullOrEmpty()
        }
    }

    fun updateSignState(state : SignUpState){
        Log.d(TAG, "updateSignState: $state")
        _livedata.value = state
    }

    fun checkSignUpUserData(){
        if(_activityState.value!!){
            _activityState.value = false
            _signUpFragments.value = fragmentsList[++fragmentPage % fragmentsList.size]
        }
    }
}