package com.ctu.planitstudy.feature.presentation.home.fragment.rewards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.domain.use_case.reward.RewardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    private val rewardUseCase: RewardUseCase
) : BaseViewModel() {

    val TAG = "RewardViewModel - 로그"

    private val _rewardDto = MutableLiveData<RewardDto>(RewardDto(0, 0, 0))
    val rewardDto: LiveData<RewardDto> = _rewardDto

    private val _newPoint = MutableLiveData<Int?>(null)
    val newPoint : LiveData<Int?> = _newPoint

    fun updateRewardDto(rewardDto: RewardDto){
        _rewardDto.value = rewardDto
    }

    fun getReward() {
        rewardUseCase.getRewardUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _rewardDto.value = it.data!!
                    loadingDismiss()
                }
                is Resource.Error -> {
                    loadingErrorDismiss()
                }
                is Resource.Loading -> {
                    loadingShow()
                }
            }
        }.launchIn(viewModelScope)
    }
    fun convertStarToPoint() {
        rewardUseCase.convertStarToPointUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    val getPoint = it.data!!.point - rewardDto.value!!.point
                    _newPoint.value = getPoint
                    _newPoint.value = null
                    _rewardDto.value = it.data!!
                    loadingDismiss()
                }
                is Resource.Error -> {
                    loadingDismiss()
                }
                is Resource.Loading -> {
                    loadingShow()
                }
            }
        }.launchIn(viewModelScope)
    }
}
