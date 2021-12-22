package com.ctu.planitstudy.feature.domain.use_case.dday

import com.ctu.core.util.Resource
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter.dtoDateTOLong
import com.ctu.planitstudy.feature.data.remote.dto.Dday.DdayListDto
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.ctu.planitstudy.feature.domain.use_case.BaseUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetDdayListUseCase @Inject constructor(
    private val ddayRepository: DdayRepository
) : BaseUseCase<DdayListDto>() {

    operator fun invoke(): Flow<Resource<DdayListDto>> = useCase { ddayRepository.getDdayList() }

    private fun sortedDdayList(ddayListDto: DdayListDto): DdayListDto {
        val ddaySort = ddayListDto.ddays.sortedBy { dtoDateTOLong(it.endAt) }
        return DdayListDto(ddaySort)
    }

    override fun modified(result: DdayListDto): DdayListDto {
        for (dday in result.ddays) {
            dday.dDay = DateCalculation().calDateBetween(
                DateCalculation().getCurrentDateString(0),
                dday.endAt
            )
        }
        return sortedDdayList(result)
    }
}
