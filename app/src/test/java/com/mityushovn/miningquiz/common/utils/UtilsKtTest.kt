package com.mityushovn.miningquiz.common.utils

import org.junit.Assert.*

import org.junit.Test

class UtilsKtTest {

    @Test
    fun testLongToDate() {
        // given
        val longDate = 1546300800000L
        // when
        val date = longDate.toDate()
        // then
        assertEquals("Tue Jan 01 03:00:00 MSK 2019", date.toString())
    }

    @Test
    fun testToIntForDB() {
        // given
        val resultTrue = true
        val resultFalse = false
        // when
        val resultTrueInt = resultTrue.toIntForDB()
        val resultFalseInt = resultFalse.toIntForDB()
        // then
        assertEquals(1, resultTrueInt)
        assertEquals(0, resultFalseInt)
    }

    @Test
    fun testToBooleanForDB() {
        // given
        val resultTrue = 1
        val resultFalse = 0
        // when
        val resultTrueBoolean = resultTrue.toBooleanForDB()
        val resultFalseBoolean = resultFalse.toBooleanForDB()
        // then
        assertEquals(true, resultTrueBoolean)
        assertEquals(false, resultFalseBoolean)
    }

}