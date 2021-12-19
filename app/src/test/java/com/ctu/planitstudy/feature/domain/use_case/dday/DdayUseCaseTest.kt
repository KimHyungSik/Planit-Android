package com.ctu.planitstudy.feature.domain.use_case.dday

import app.cash.turbine.test
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.DdayIconSet
import com.ctu.planitstudy.feature.data.repository.FakeDdayRepository
import com.ctu.planitstudy.feature.domain.model.Dday
import com.ctu.planitstudy.feature.domain.repository.DdayRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class DdayUseCaseTest {
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
    fun `get dday list use case test`() = runBlocking {
        ddayUseCase.getDdayListUseCase().test {
            val loading = awaitItem()
            val ddayListDto = awaitItem().data!!
            val result = ddayListDto.ddays
            for (n in 0..result.size - 2) {
                if (result[n].endAt != result[n + 1].endAt)
                    assertThat(DateConvter.dtoDateTOLong(result[n].endAt)).isLessThan(
                        DateConvter.dtoDateTOLong(
                            result[n + 1].endAt
                        )
                    )
            }
            cancelAndConsumeRemainingEvents()
        }
    }
}
