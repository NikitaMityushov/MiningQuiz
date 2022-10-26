package com.mityushovn.miningquiz.main.presentation.quizList.examsfragment

import com.mityushovn.miningquiz.kmm_domain.domain.models.Exam
import com.mityushovn.miningquiz.core_domain.domain.repositories.ExamsRepositoryAPI
import com.mityushovn.miningquiz.core_testing.unit.coroutines.CoroutineSubject
import com.mityushovn.miningquiz.core_testing.unit.coroutines.MainDispatcherRule
import com.mityushovn.miningquiz.core_domain.domain.models.Loading
import com.mityushovn.miningquiz.core_domain.domain.models.Ready
import com.mityushovn.quizlist_feature.internal.presentation.examsfragment.ExamsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verifyBlocking
import org.mockito.kotlin.whenever
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class ExamsViewModelTest {
    lateinit var examsRepository: ExamsRepositoryAPI
    lateinit var viewModel: ExamsViewModel
    lateinit var testExamsList: List<Exam>
    lateinit var testFlow: MutableStateFlow<List<Exam>>

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        examsRepository = mock()
        testExamsList = createTestExamsList()
        testFlow = MutableStateFlow(emptyList())
    }

    @Test
    fun `when init ViewModel ExamsRepository method getAllExams is called`() = runTest {
        whenever(examsRepository.getAllExams()).thenReturn(testFlow)
        launch {
            viewModel = ExamsViewModel(
                examsRepository
            )
            testFlow.emit(testExamsList)
        }.join()
        advanceUntilIdle()
        verifyBlocking(examsRepository) { getAllExams() }
    }

    @Test
    fun `when updateExamsList after initialization exams StateFlow was changed twice`() = runTest {
        // given
        whenever(examsRepository.getAllExams()).thenReturn(testFlow)
        // when
        launch {
            viewModel = ExamsViewModel(
                examsRepository
            )
            testFlow.emit(testExamsList)
        }.join()
        advanceUntilIdle()

        launch {
            viewModel.updateExamsList()
            testFlow.emit(testExamsList)
        }.join()
        // then
        verifyBlocking(examsRepository, times(2)) { getAllExams() }
    }

    @Test
    fun `when updateExamsList return flow with Exception then errorEvent is changed and must be consumed only ones`() =
        runTest {
            // given
            whenever(examsRepository.getAllExams()).thenReturn(flow { throw Exception("ERRORRRR") })
            // when
            launch {
                viewModel = ExamsViewModel(
                    examsRepository
                )
            }.join()
            advanceUntilIdle()

            launch {
                viewModel.updateExamsList()
            }.join()
            advanceUntilIdle()
            // then
            assert(viewModel.showLoadingExamsErrorMessage.value.get() == "ERRORRRR")
            assert(viewModel.showLoadingExamsErrorMessage.value.get() == null)
        }

    @Test
    fun `when updateExamsList is called then loadingState should be Loading but after emitting value should by Ready`() =
        runTest {
            // given
            val subject = CoroutineSubject<List<Exam>>()
            whenever(examsRepository.getAllExams()).thenReturn(flow { emit(subject.get()) })
            // when
            viewModel = ExamsViewModel(
                examsRepository
            )
            // then
            assertEquals(Loading, viewModel.loadingState.value)
            // when
            subject.sendSuccess(testExamsList)
            // then
            assertEquals(Ready, viewModel.loadingState.value)
            assertEquals(testExamsList, viewModel.exams.value)
        }

}

/*
    helpers
 */
private fun createTestExamsList(): List<Exam> {
    return listOf(
        Exam(1, "Exam1"),
        Exam(2, "Exam2"),
        Exam(3, "Exam3"),
        Exam(4, "Exam4"),
    )
}