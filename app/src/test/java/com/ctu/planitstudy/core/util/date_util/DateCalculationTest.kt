package com.ctu.planitstudy.core.util.date_util

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import com.ctu.planitstudy.core.util.enums.Weekday

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
        for(week in 0..Weekday.values().size - 2)
            assertThat(result[week].ordinal).isGreaterThan(result[week+1].ordinal)
        val emptyResult  = dateCalculation.calDateBetweenWeek("2021-12-1", "2021-11-1")
        assertEquals(emptyResult, arrayListOf<Weekday>())
    }
}