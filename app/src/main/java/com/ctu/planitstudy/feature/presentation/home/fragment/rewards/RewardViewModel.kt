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

    private val _rewardDto = MutableLiveData<RewardDto>(RewardDto(0, 0, 0))
    val rewardDto: LiveData<RewardDto> = _rewardDto

    init {
        getReward()
    }

    fun getReward() {
        rewardUseCase.getRewardUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _rewardDto.value = it.data!!
                    loadingShow()
                }
                is Resource.Error -> {
                    loadingDismiss()
                }
                is Resource.Loading -> {
                    loadingDismiss()
                }
            }
        }.launchIn(viewModelScope)
    }
}
