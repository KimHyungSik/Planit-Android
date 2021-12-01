package com.ctu.planitstudy.feature.domain.use_case.dday

import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.DdayIconSet
import com.ctu.planitstudy.feature.data.repository.FakeDdayRepository
import com.ctu.planitstudy.feature.domain.model.Dday
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.util.*
import com.google.common.truth.Truth.assertThat

class DdayUseCaseTest{
    lateinit var ddayUseCase: DdayUseCase
    lateinit var ddayRepository: DdayRepository
    lateinit var ddayIconSet: DdayIconSet
    lateinit var random: Random

    @Before
    fun setUp() {
        ddayIconSet = DdayIconSet()
        ddayRepository = FakeDdayRepository()
        random = Random()
        ddayUseCase = DdayUseCase(
            addDayUseCase = AddDdayUseCase(ddayRepository),
            deleteDdayUseCase = DeleteDdayUseCase(ddayRepository),
            getDdayListUseCase = GetDdayListUseCase(ddayRepository),
            modifiedDdayUseCase = ModifiedDdayUseCase(ddayRepository)
        )
        val ddayToInsert = mutableListOf<Dday>()
        ('가'..'차').forEachIndexed { index, c ->
            ddayToInsert.add(
                Dday(
                    title = c.toString(),
                    endAt = DateCalculation().getCurrentDateString(index),
                    icon = ddayIconSet.dDayIconList[random.nextInt(ddayIconSet.dDayIconList.size - 1)],
                    isRepresentative = false
                )
            )
        }

        ddayToInsert.shuffle()

        runBlocking {
            ddayToInsert.forEach {
                ddayRepository.addDday(it)
            }
        }
    }

    @Test
    fun getDdayListUseCaseSortTest() = runBlocking {
        val result = ddayUseCase.getDdayListUseCase.sortedDdayList(ddayRepository.getDdayList()).ddays
        for(n in 0..result.size - 2) {
            if(result[n].endAt != result[n + 1].endAt)
                assertThat(DateConvter.dtoDateTOLong(result[n].endAt)).isGreaterThan(
                    DateConvter.dtoDateTOLong(
                        result[n + 1].endAt
                    )
                )
        }
    }
}