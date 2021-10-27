package com.ctu.planitstudy.feature.presentation.sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctu.planitstudy.feature.presentation.sign_up.fragment.SignUpFragments
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

    val fragmentsList = listOf(
        SignUpFragments.Name,
        SignUpFragments.Gender,
        SignUpFragments.DateOfBirth,
        SignUpFragments.Category,
    )

    init {
        liveData.observeForever {  signUpState ->
            var pageCount = 0
            pageCount += if(!signUpState.nickname.isNullOrBlank() && !signUpState.name.isNullOrEmpty()) 1 else 0
            pageCount += if(!signUpState.gender.isNullOrBlank()) 1 else 0
            pageCount += if(!signUpState.dateOfBirth.isNullOrBlank()) 1 else 0
            pageCount += if(!signUpState.category.isNullOrBlank()) 1 else 0
            if(fragmentPage < pageCount) _activityState.value = true
        }
    }

    fun updateSignState(state : SignUpState){
        Log.d(TAG, "updateSignState: $state")
        _livedata.value = state
    }

    fun checkSignUpUserData(){
        Log.d(TAG, "checkSignUpUserData: ${fragmentsList.size}")
        if(_activityState.value!!){
            _activityState.value = false
            _signUpFragments.value = fragmentsList[++fragmentPage]
        }
        Log.d(TAG, "checkSignUpUserData: $fragmentPage")
    }
}