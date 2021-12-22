package com.ctu.planitstudy.feature.presentation.planitpass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.domain.use_case.reward.GetPlanetPassListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class PlanitPassViewModel @Inject constructor(
    private val getPlanetPassList: GetPlanetPassListUseCase
) : BaseViewModel() {

    val TAG = "PlanitPassViewModel - 로그"

    private val _planetPassList = MutableLiveData<PlanetPassList>()
    val planetPassList: LiveData<PlanetPassList> = _planetPassList

    init {
        getPlanetPass()
    }

    private fun getPlanetPass() {
        getPlanetPassList().onEach { it ->
            when (it) {
                is Resource.Success -> {
                    val newPlanetList = mutableListOf<PlanetPass>()
                    it.data!!.planets.let { plaent ->
                        for (n in plaent)
                            newPlanetList.add(PlanetPass(n.planetId, n.name, n.description))
                    }

                    _planetPassList.value = PlanetPassList(newPlanetList)
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
}
