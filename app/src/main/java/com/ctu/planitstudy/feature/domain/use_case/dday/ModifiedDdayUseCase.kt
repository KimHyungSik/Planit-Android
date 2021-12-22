package com.ctu.planitstudy.feature.domain.use_case.dday

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayDto
import com.ctu.planitstudy.feature.domain.model.Dday
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ModifiedDdayUseCase@Inject constructor(
    private val ddayRepository: DdayRepository
) : BaseUseCase<DdayDto>() {
    val TAG = "Modified - 로그"
    operator fun invoke(dday: Dday, ddayId: Int): Flow<Resource<DdayDto>> = useCase { ddayRepository.modifiedDday(dday, ddayId) }
}
