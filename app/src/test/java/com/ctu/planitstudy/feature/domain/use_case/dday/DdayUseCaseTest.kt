package com.ctu.planitstudy.feature.domain.use_case.dday

import android.util.Log
import app.cash.turbine.test
import com.ctu.planitstudy.core.util.date_util.DateCalculation
import com.ctu.planitstudy.core.util.date_util.DateConvter
import com.ctu.planitstudy.core.util.enums.DdayIconSet
import com.ctu.planitstudy.feature.data.repository.FakeDdayRepository
import com.ctu.planitstudy.feature.domain.model.Dday
import com.google.common.truth.Truth.assertThat
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Random

class DdayUseCaseTest {

    private lateinit var ddayUseCase: DdayUseCase
    private lateinit var ddayRepository: FakeDdayRepository
    private lateinit var ddayIconSet: DdayIconSet
    private lateinit var random: Random
    private val ddayToInsert = mutableListOf<Dday>()

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
    fun `디데이 리스트 조회 및 정렬 테스트`() = runBlocking {
        ddayUseCase.getDdayListUseCase().test {
            awaitItem()
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

            assertEquals(ddayToInsert.size, result.size)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `디데이 리스트 추가 테스트`() = runBlocking {
        val newDday = Dday(
            title = "새로운 디데이",
            endAt = DateCalculation().getCurrentDateString(null),
            icon = ddayIconSet.dDayIconList[random.nextInt(ddayIconSet.dDayIconList.size - 1)],
            isRepresentative = false
        )
        ddayToInsert.add(newDday)
        ddayUseCase.addDayUseCase(newDday).test {
            assertEquals(ddayToInsert.size, ddayRepository.ddays.size)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `디데이 수정 테스트`() = runBlocking {
        val newDday = Dday(
            title = "새로운 디데이",
            endAt = DateCalculation().getCurrentDateString(null),
            icon = ddayIconSet.dDayIconList[random.nextInt(ddayIconSet.dDayIconList.size - 1)],
            isRepresentative = false
        )
        ddayUseCase.modifiedDdayUseCase(newDday, 0).test {
            assertEquals(newDday, ddayRepository.ddays[0])
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `디데이 삭제 테스트`() = runBlocking {
        ddayUseCase.deleteDdayUseCase(0).test {
            assertEquals(
                ddayToInsert.size - 1, ddayRepository.ddays.size
            )
            cancelAndConsumeRemainingEvents()
        }
    }
}
