package com.mityushovn.miningquiz.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.*
import java.lang.IllegalArgumentException

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test(expected = IllegalArgumentException::class)
    fun `when customRetryFlow is called with negative number of retries then should be thrown IllegalArgumentException`() =
        runTest {
            // given
            val numberOfRetries = -3L
            // when
            launch {
                val result =
                    customRetryFlow(numberOfRetries) { flow { emit("some_arg") } }.collect { }
            }.join()
            advanceUntilIdle()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when customRetryFlow is called with three number of retries then `() =
        runTest {
            // given
            val numberOfRetries = 10L
            val allNumberOfTries = numberOfRetries.toInt() + 1
            val mockFunction = mock<() -> Unit>()
            // when
            launch {
                customRetryFlow(numberOfRetries) {
                    flow<String> {
                        mockFunction()
                        throw IllegalArgumentException()
                    }
                }
                    // then
                    .catch { verify(mockFunction, times(allNumberOfTries)).invoke() }
                    .collect { }

            }.join()
            advanceUntilIdle()
        }

}