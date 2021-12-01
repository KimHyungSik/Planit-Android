package com.ctu.planitstudy.core.util.date_util

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DateCalculationTest : TestCase() {

    lateinit var dateCalculation: DateCalculation

    @Before
    override fun setUp(){
        dateCalculation = DateCalculation()
    }

    fun testGetDateFormat() {}

    fun testGetCal() {}

    fun testDateCalculationDateBetween() = runBlocking{
        val result = dateCalculation.calDateBetween("2021-12-1", "2021-12-10")
        assertEquals(result, 9)
    }

    fun testGetCurrentDateString() {}

    fun testCalDateBetweenWeek() {}
}