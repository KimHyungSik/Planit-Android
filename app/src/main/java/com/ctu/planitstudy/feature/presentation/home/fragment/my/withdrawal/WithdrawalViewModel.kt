package com.ctu.planitstudy.feature.presentation.home.fragment.my.withdrawal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.domain.use_case.reward.RewardUseCase
import com.ctu.planitstudy.feature.domain.use_case.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val rewardUseCase: RewardUseCase,
    private val userUseCase: UserUseCase
) : BaseViewModel() {

    companion object {
        val TAG = "WithdrawalViewModel - 로그"
    }

    private val _rewardDto = MutableLiveData<RewardDto>()
    val rewardDto: LiveData<RewardDto> = _rewardDto

    private val _deleteState = MutableLiveData<Boolean>()
    val deleteState: LiveData<Boolean> = _deleteState

    fun getReward() {
        rewardUseCase.getRewardUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _rewardDto.value = it.data!!
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteUser() {
        userUseCase.deleteUserUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _deleteState.value = true
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }
        }.launchIn(viewModelScope)
    }
}
