package com.ctu.planitstudy.feature.domain.use_case.dday

import com.ctu.core.util.Resource
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteDdayUseCase @Inject constructor(
    private val ddayRepository: DdayRepository
) : BaseUseCase<Unit>() {

    val TAG = "DeleteUseCase - 로그"
    operator fun invoke(ddayId: Int): Flow<Resource<Unit>> = useCase { ddayRepository.deleteDday(ddayId) }
}
