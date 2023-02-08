package com.mityushovn.statistics_feature.statisticsfragment

import android.app.Application
import android.content.res.Resources
import com.mityushovn.miningquiz.core_domain.domain.models.statisticsEntities.AbstractStatistics
import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.core_testing.coroutines.CoroutineSubject
import com.mityushovn.miningquiz.core_testing.coroutines.MainDispatcherRule
import com.mityushovn.miningquiz.feature_statistics.internal.presentation.StatisticsViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class StatisticsViewModelTest {

    private lateinit var application: Application
    private lateinit var resources: Resources
    private lateinit var attemptsRepositoryAPI: AttemptsRepositoryAPI
    private lateinit var viewModel: StatisticsViewModel
    private lateinit var testStats: AbstractStatistics

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() = runBlocking {
        application = mock()
        resources = mock()
        attemptsRepositoryAPI = mock()

        testStats = mock()
        whenever(testStats.numberOfAttempts).thenReturn(3)
        whenever(testStats.numberOfFailedAttempts).thenReturn(1)
        whenever(testStats.numberOfSuccessAttempts).thenReturn(2)

        whenever(application.resources).thenReturn(resources)
        whenever(resources.getString(any())).thenReturn("TEST_VALUE")
        whenever(attemptsRepositoryAPI.getTopicsStatistics()).thenReturn(flow { emit(testStats) })
        whenever(attemptsRepositoryAPI.getExamsStatistics()).thenReturn(flow { emit(testStats) })
        viewModel = createStatisticsViewModel(
            attemptsRepositoryAPI,
            mainDispatcherRule.testDispatcher,
            application
        )
    }

    @Test

    fun `if viewModel's delete is called then repository's deleteAllStatistics method was called once`() =
        runTest {
            // given
            whenever(attemptsRepositoryAPI.deleteAllStatistics()).thenReturn(flow { emit(true) })
            // when
            launch {
                viewModel = StatisticsViewModel(
                    attemptsRepositoryAPI,
                    mainDispatcherRule.testDispatcher,
                    application
                )
                viewModel.deleteAllStatistics()
            }.join()
            advanceUntilIdle()
            // then
            verify(attemptsRepositoryAPI).deleteAllStatistics()

        }

    @Test
    fun `when after successfully deleting all statistics, eventDeletedStats should be true and can be consumed only once`() =
        runTest {
            // given
            whenever(attemptsRepositoryAPI.deleteAllStatistics()).thenReturn(flow { emit(true) })
            // when
            launch {
                viewModel = StatisticsViewModel(
                    attemptsRepositoryAPI,
                    mainDispatcherRule.testDispatcher,
                    application
                )
                viewModel.deleteAllStatistics()
            }.join()
            advanceUntilIdle()
            // then
            assertTrue(viewModel.eventDeletedStats.value.get() == true)
            assertNull(viewModel.eventDeletedStats.value.get()) // only once
        }

    @Test
    fun `when after unsuccessfully deleting all statistics, eventDeletedStats should be false and can be consumed only once`() =
        runTest {
            // given
            whenever(attemptsRepositoryAPI.deleteAllStatistics()).thenReturn(flow { emit(false) })
            // when
            launch {
                viewModel = StatisticsViewModel(
                    attemptsRepositoryAPI,
                    mainDispatcherRule.testDispatcher,
                    application
                )
                viewModel.deleteAllStatistics()
            }.join()
            advanceUntilIdle()
            // then
            assertTrue(viewModel.eventDeletedStats.value.get() == false)
            assertNull(viewModel.eventDeletedStats.value.get()) // only once
        }

    @Test
    fun `when init viewModel then all strings should be TEST_VALUE`() = runTest {
        // given
        // when
        launch {
            viewModel = StatisticsViewModel(
                attemptsRepositoryAPI,
                mainDispatcherRule.testDispatcher,
                application
            )
        }.join()
        advanceUntilIdle()
        // then
        assertEquals("TEST_VALUE", viewModel.stringAttemptsExam.value)
        assertEquals("TEST_VALUE", viewModel.stringAttemptsTopic.value)
    }

    @Test
    fun `when calling delete stats with Exception then should retry attempt once to delete stats`() =
        runTest {
            // given
            whenever(attemptsRepositoryAPI.deleteAllStatistics())
                .thenReturn(flow { throw Exception() })
                .thenReturn(flow { emit(true) })
            // when
            launch {
                viewModel = StatisticsViewModel(
                    attemptsRepositoryAPI,
                    mainDispatcherRule.testDispatcher,
                    application
                )
                viewModel.deleteAllStatistics()
            }.join()
            advanceUntilIdle()

            // then
            verify(attemptsRepositoryAPI, times(2)).deleteAllStatistics()
        }

    @Test
    fun `when updateStats is called then on start isLoading is true`() = runTest {
        val subject = CoroutineSubject<AbstractStatistics>()
        // given
        whenever(attemptsRepositoryAPI.getTopicsStatistics()).thenReturn(flow {
            emit(
                subject.get()
            )
        })
        // when
        launch {
            viewModel = StatisticsViewModel(
                attemptsRepositoryAPI,
                mainDispatcherRule.testDispatcher,
                application
            )
            viewModel.updateStats()
        }.join()
        advanceUntilIdle()
        // then
        assertTrue(viewModel.isLoading.value)
        // after
        subject.sendSuccess(testStats)
        // then
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `when updateStats calls throws inside Exception then showErrorMessage equals true`() =
        runTest {
            // given
            whenever(attemptsRepositoryAPI.getTopicsStatistics()).thenReturn(flow {
                throw Exception()
            })
            whenever(attemptsRepositoryAPI.getExamsStatistics()).thenReturn(flow {
                throw Exception()
            })
            // when
            launch {
                viewModel = StatisticsViewModel(
                    attemptsRepositoryAPI,
                    mainDispatcherRule.testDispatcher,
                    application
                )
            }.join()
            advanceUntilIdle()
            // then
            assertTrue(viewModel.showErrorMessage.value)
            assertFalse(viewModel.isLoading.value)
        }

    /*
     * helpers
     */
    private fun createStatisticsViewModel(
        attemptsRepositoryAPI: AttemptsRepositoryAPI,
        backgroundDispatcher: CoroutineDispatcher,
        application: Application
    ): StatisticsViewModel {
        return StatisticsViewModel(
            attemptsRepositoryAPI,
            backgroundDispatcher,
            application
        )
    }
}