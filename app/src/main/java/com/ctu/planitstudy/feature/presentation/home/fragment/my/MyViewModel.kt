package com.ctu.planitstudy.feature.presentation.home.fragment.my

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.domain.use_case.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModel() {

    val TAG = "MyViewModel - 로그"

    private val _userInformationDto = MutableLiveData<UserInformationDto>()
    val userInformationDto: LiveData<UserInformationDto> = _userInformationDto

    private val _logout = MutableLiveData<Boolean>(false)
    val logout: LiveData<Boolean> = _logout

    init {
        getUserInformation()
    }

    private fun getUserInformation() {
        userUseCase.getUserUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    Log.d(TAG, "getUserInformation: ${it.data}")
                    _userInformationDto.value = it.data!!
                }
                is Resource.Error -> {
                    Log.d(TAG, "getUserInformation: ${it.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun logoutFun(boolean: Boolean) {
        _logout.value = boolean
    }
}
