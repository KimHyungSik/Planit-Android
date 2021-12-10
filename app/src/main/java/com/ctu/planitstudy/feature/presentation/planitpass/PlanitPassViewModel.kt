package com.ctu.planitstudy.feature.presentation.planitpass

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.base.BaseViewModel
import com.ctu.planitstudy.feature.domain.use_case.reward.GetPlanetPassListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

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
        getPlanetPassList().onEach {
            when (it) {
                is Resource.Success -> {
                    val newPlanetList = mutableListOf<PlanetPass>()
                    for (n in it.data!!.planets)
                        newPlanetList.add(PlanetPass(n.planetId, n.name, n.description))

                    _planetPassList.value = PlanetPassList(newPlanetList)
                    loadingDismiss()
                }
                is Resource.Error -> {
                    Log.d(TAG, "getPlanetPass: Error ${it.message}")
                    loadingDismiss()
                }
                is Resource.Loading -> {
                    loadingShow()
                }
            }
        }.launchIn(viewModelScope)
    }
}
