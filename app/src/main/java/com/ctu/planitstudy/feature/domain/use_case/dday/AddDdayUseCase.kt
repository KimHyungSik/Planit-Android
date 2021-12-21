package com.ctu.planitstudy.feature.domain.use_case.dday

import android.util.Log
import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.domain.model.Dday
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class AddDdayUseCase @Inject constructor(
    private val ddayRepository: DdayRepository
) : BaseUseCase<DdayDto>() {

    val TAG = "AddDdayUseCase - 로그"
    operator fun invoke(dday: Dday): Flow<Resource<DdayDto>> = useCase { ddayRepository.addDday(dday) }
}
