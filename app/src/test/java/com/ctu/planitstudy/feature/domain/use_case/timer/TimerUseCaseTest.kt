package com.ctu.planitstudy.feature.domain.use_case.timer

import app.cash.turbine.test
import com.ctu.planitstudy.feature.data.remote.dto.timer.TimerMeasurementDto
import com.ctu.planitstudy.feature.data.repository.FakeTimerRepository
import com.ctu.planitstudy.feature.domain.model.timer.RecordMeasurementTimer
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TimerUseCaseTest {

    lateinit var timerUseCase: TimerUseCase
    lateinit var timerRepository: FakeTimerRepository

    private var timerMeasurementDto = TimerMeasurementDto(0, 1, false, 100, 0, 0, "0000-00-00")

    @Before
    fun setUp() {
        timerRepository = FakeTimerRepository()
        timerUseCase = TimerUseCase(
            getMeasurementTimerUseCase = GetMeasurementTimerUseCase(timerRepository),
            recordMeasurementTimerUseCase = RecordMeasurementTimerUseCase(timerRepository)
        )
    }

    @Test
    fun `타이머 조회 테스트`() = runBlocking {
        timerUseCase.getMeasurementTimerUseCase("1").test {
            awaitItem()
            val result = awaitItem().data
            assertEquals(result, timerMeasurementDto)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `타이머 기록 테스트`() = runBlocking {
        val recordTimer = RecordMeasurementTimer(1, true, 500, 1, 5)
        timerUseCase.recordMeasurementTimerUseCase("1", recordTimer).test {
            awaitItem()
            awaitItem()
            cancelAndConsumeRemainingEvents()
        }
        timerUseCase.getMeasurementTimerUseCase("1").test {
            awaitItem()
            val result = awaitItem().data
            assertEquals(result, timerMeasurementDto.copy(bonusTicket = recordTimer.bonusTicket, isDone = recordTimer.isDone, recordedTime = recordTimer.recordedTime, rest = recordTimer.rest, star = recordTimer.star))
            cancelAndConsumeRemainingEvents()
        }
    }
}