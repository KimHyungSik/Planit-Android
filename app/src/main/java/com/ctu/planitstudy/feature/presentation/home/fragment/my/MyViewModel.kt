package com.ctu.planitstudy.feature.presentation.home.fragment.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.data.remote.dto.user.UserInformationDto
import com.ctu.planitstudy.feature.domain.use_case.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : BaseViewModel() {

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
                    loadingShow()
                }
                is Resource.Success -> {
                    _userInformationDto.value = it.data!!
                    loadingDismiss()
                }
                is Resource.Error -> {
                    loadingErrorDismiss()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun logoutFun(boolean: Boolean) {
        _logout.value = boolean
    }
}
