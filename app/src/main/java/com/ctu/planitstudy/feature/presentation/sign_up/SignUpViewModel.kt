package com.ctu.planitstudy.feature.presentation.sign_up

import android.hardware.input.InputManager
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

    // 현재 회원 가입 정보
    private val _livedata = MutableLiveData<SignUpState>(SignUpState())
    val liveData : LiveData<SignUpState> = _livedata

    // 화면 전화 여부 확인
    private val _activityState = MutableLiveData<Boolean>(false)
    val activityState : LiveData<Boolean> = _activityState

    // 현재 표시 중인 화면
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
        // 현재 회원가입 상태 별로 화면 전환 검사
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
        _livedata.value = state
    }

    fun checkSignUpUserData(){
        if(_activityState.value!!){
            _activityState.value = false
            _signUpFragments.value = fragmentsList[++fragmentPage]
        }

    }
}