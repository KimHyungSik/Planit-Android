package com.ctu.planitstudy.core.util.date_util

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import com.ctu.planitstudy.core.util.enums.Weekday
import com.google.common.truth.Truth.assertThat

class DateCalculationTest : TestCase() {
    private lateinit var dateCalculation: DateCalculation

    @Before
    override fun setUp(){
        dateCalculation = DateCalculation()
    }

    @Test
    fun testDateCalculationDateBetween() = runBlocking{
        val result = dateCalculation.calDateBetween("2021-12-1", "2021-12-10")
        assertEquals(result, 9)
    }

    @Test
    fun testGetCurrentDateString() = runBlocking {
        val result = dateCalculation.getCurrentDateString(null)
        assertEquals(result, LocalDate.now().toString())
    }

    @Test
    fun testCalDateBetweenWeek() = runBlocking {
        val result = dateCalculation.calDateBetweenWeek("2021-12-1", "2021-12-4")
        assertEquals(result,
            arrayListOf
                (
                Weekday.Wednesday,
                Weekday.Thursday,
                Weekday.Friday,
                Weekday.Saturday
            )
        )

        val emptyResult  = dateCalculation.calDateBetweenWeek("2021-12-1", "2021-11-1")
        assertEquals(emptyResult, arrayListOf<Weekday>())
    }

    @Test
    fun testCalDateBetweenWeekAscendingSort() = runBlocking {
        val result = dateCalculation.calDateBetweenWeek("2021-12-1", "2021-12-4")
        // 오름 차순 정렬 확인
        for(week in 0..result.size - 2)
            assertThat(result[week].ordinal).isLessThan(result[week+1].ordinal)
    }
}