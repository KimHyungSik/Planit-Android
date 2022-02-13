package com.ctu.planitstudy.feature.presentation.planitpass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.data.remote.dto.reward.PlanetListDto
import com.ctu.planitstudy.feature.data.remote.dto.reward.RewardDto
import com.ctu.planitstudy.feature.domain.use_case.reward.RewardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PlanitPassViewModel @Inject constructor(
    private val rewardUseCase: RewardUseCase
) : BaseViewModel() {

    val TAG = "PlanitPassViewModel - 로그"

    private val _planetPassList = MutableLiveData<PlanetPassList>()
    val planetPassList: LiveData<PlanetPassList> = _planetPassList

    var rewardDto = RewardDto(0, 0, 0)
    private val _newPoint = MutableLiveData<Int>()
    val newPoint: LiveData<Int> = _newPoint

//    suspend fun getPlanetPass() = rewardUseCase.getPlanetPassListUseCase.getRewardPlanet()

    init {
        getPlanetPass()
    }

    fun setUpPlanetPass(planetListDto: PlanetListDto) {
        val newPlanetList = mutableListOf<PlanetPass>()
        planetListDto.planets.let { plaent ->
            for (n in plaent)
                newPlanetList.add(PlanetPass(n.planetId, n.name, n.description))
        }

        _planetPassList.value = PlanetPassList(newPlanetList)
    }

    private fun getPlanetPass() {
        rewardUseCase.getPlanetPassListUseCase().onEach { it ->
            when (it) {
                is Resource.Success -> {
                    val newPlanetList = mutableListOf<PlanetPass>()
                    it.data!!.planets.let { plaent ->
                        for (n in plaent)
                            newPlanetList.add(PlanetPass(n.planetId, n.name, n.description))
                    }

                    _planetPassList.value = PlanetPassList(newPlanetList)
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
                is Resource.AppUpdate -> {
                    showAppUpdate()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun convertPassToPoint(pass: Int) {
        rewardUseCase.convertPlanitPassToPointUseCase(pass).onEach {
            when (it) {
                is Resource.Success -> {
                    val getPoint = it.data!!.star - rewardDto.star
                    rewardDto = it.data!!
                    _newPoint.value = getPoint
                    loadingDismiss()
                }
                is Resource.Error -> {
                    loadingDismiss()
                }
                is Resource.Loading -> {
                    loadingShow()
                }
                is Resource.AppUpdate -> {
                    showAppUpdate()
                }

            }
        }.launchIn(viewModelScope)
    }
}
