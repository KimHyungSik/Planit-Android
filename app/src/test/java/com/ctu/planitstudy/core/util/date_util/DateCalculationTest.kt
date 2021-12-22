package com.ctu.planitstudy.core.util.date_util

import com.ctu.planitstudy.core.util.enums.Weekday
import com.google.common.truth.Truth.assertThat
import java.time.LocalDate
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class DateCalculationTest : TestCase() {
    private lateinit var dateCalculation: DateCalculation
    private lateinit var dtoStringPattern: Regex

    @Before
    override fun setUp() {
        dateCalculation = DateCalculation()
        dtoStringPattern = Regex(pattern = "^y(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])\$")
    }

    @Test
    fun testDateCalculationDateBetween() {
        val result = dateCalculation.calDateBetween("2021-12-1", "2021-12-10")
        assertEquals(result, 9)
    }

    @Test
    fun testGetCurrentDateString() {
        val result = dateCalculation.getCurrentDateString(null)
        assertEquals(result, LocalDate.now().toString())
    }

    @Test
    fun testCalDateBetweenWeek() {
        val result = dateCalculation.calDateBetweenWeek("2021-12-1", "2021-12-4")
        assertEquals(
            result,
            arrayListOf
            (
                Weekday.Wednesday,
                Weekday.Thursday,
                Weekday.Friday,
                Weekday.Saturday
            )
        )

        val emptyResult = dateCalculation.calDateBetweenWeek("2021-12-1", "2021-11-1")
        assertEquals(emptyResult, arrayListOf<Weekday>())
    }

    @Test
    fun testCalDateBetweenWeekAscendingSort() {
        val result = dateCalculation.calDateBetweenWeek("2021-12-1", "2021-12-4")
        // 오름 차순 정렬 확인
        for (week in 0..result.size - 2)
            assertThat(result[week].ordinal).isLessThan(result[week + 1].ordinal)
    }
}
