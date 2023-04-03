package com.mityushovn.miningquiz.main.presentation.activity

import com.mityushovn.miningquiz.core_domain.domain.models.Error
import com.mityushovn.miningquiz.core_domain.domain.models.Question
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {
    lateinit var questionsRepository: QuestionsRepositoryAPI
    lateinit var testFlow: MutableStateFlow<List<Question>>
    lateinit var viewModel: MainActivityViewModel
    lateinit var expectedList: List<Question>

    @get:Rule
    val mainDispatcherRule = com.mityushovn.miningquiz.core_testing.coroutines.MainDispatcherRule()

    @Before
    fun setUp() = runBlocking {
        questionsRepository = mock()
        expectedList = createTestList()
        testFlow = MutableStateFlow(emptyList())
        whenever(questionsRepository.getQuestionsMatchesSearchInput(any())).thenReturn(testFlow)
        viewModel = MainActivityViewModel(questionsRepository)
    }

    @Test
    fun `when call handle input viewModel queryFlow should be changed`() = runTest {
        val input = "test"
        launch {
            viewModel.handleInput(input)
        }.join()
        advanceUntilIdle() // wait for all coroutines to finish and skips all delays
        assertEquals(input, viewModel.queryFlow.value)
    }

    @Test
    fun `when call handleInput with not blank input then getQuestionsMatchesSearchInput is called with the same input`() =
        runTest {
            // given
            val input1 = "TEST"
            // when
            launch {
                viewModel.handleInput(input1)
            }.join()
            advanceUntilIdle() //!!!!
            // then
            val result1 = viewModel.queryFlow.value
            assertEquals(input1, result1)
            verifyBlocking(questionsRepository, times(1)) { getQuestionsMatchesSearchInput(input1) }

            // given
            val input2 = "TEST2"
            // when
            launch {
                viewModel.handleInput(input2)
            }.join()
            advanceUntilIdle()
            // then
            val result2 = viewModel.queryFlow.value
            assertEquals(input2, result2)
            verifyBlocking(
                questionsRepository,
                times(1)
            ) { getQuestionsMatchesSearchInput(input2) }
        }

    @Test
    fun `when call handleInput with blank input then getQuestionsMatchesSearchInput is not called`() =
        runTest {
            // given
            val input1 = ""
            // when
            launch {
                viewModel.handleInput(input1)
            }.join()
            advanceUntilIdle() //!!!!
            // then
            verifyBlocking(questionsRepository, never()) { getQuestionsMatchesSearchInput(input1) }

            // given
            val input2 = "          "
            // when
            launch {
                viewModel.handleInput(input2)
            }.join()
            advanceUntilIdle()
            // then
            verifyBlocking(
                questionsRepository,
                never()
            ) { getQuestionsMatchesSearchInput(input2) }
        }

    @Test
    fun `when call handleInput with TEST string then return 2 mock questions`() = runTest {
        // given
        val input = "TEST"
        // when
        launch {
            viewModel.handleInput(input)
            testFlow.emit(expectedList)
        }.join()
        advanceUntilIdle()

        // then
        val result = viewModel.questions.value
        assertEquals(expectedList, result)
    }

    @Test
    fun `when first call of handleInput throws exception then should retry attempt`() =
        runTest {
            // given
            val input = "TEST"
            whenever(questionsRepository.getQuestionsMatchesSearchInput(any()))
                .thenReturn(flow { throw Exception() })
                .thenReturn(testFlow)
            // when
            launch {
                viewModel.handleInput(input)
                testFlow.emit(expectedList)
            }.join()
            advanceUntilIdle()

            // then
            val result = viewModel.questions.value
            assertEquals(expectedList, result)
        }

    @Test
    fun `when input the same string then should not call getQuestionsMatchesSearchInput`() =
        runTest {
            // given
            val input = "TEST"
            val input2 = "sljdg"
            // when
            launch {
                viewModel.handleInput(input)
                testFlow.emit(expectedList)
            }.join()
            advanceUntilIdle()

            launch {
                viewModel.handleInput(input)
                testFlow.emit(expectedList)
            }.join()
            advanceUntilIdle()

            launch {
                viewModel.handleInput(input2)
                testFlow.emit(expectedList)
            }.join()
            advanceUntilIdle()

            launch {
                viewModel.handleInput(input2)
                testFlow.emit(expectedList)
            }.join()
            advanceUntilIdle()

            // then
            verifyBlocking(questionsRepository, times(2)) { getQuestionsMatchesSearchInput(any()) }
        }

    @Test
    fun `when repository throws exception then viewModel's SearchState should be Error`() =
        runTest {
            // given
            val input1 = "TEST"
            val expectedException = Exception("TEST_EXCEPTION_MESSAGE")
            val expected = Error(expectedException)
            whenever(questionsRepository.getQuestionsMatchesSearchInput(input1))
                .thenReturn(flow { throw expectedException })

            // when
            launch {
                viewModel.handleInput(input1)
            }.join()
            advanceUntilIdle()

            // then
            val result = (viewModel.searchState.value as Error).e
            assertEquals(expected.e.message, result.message)
        }

    @Test
    fun `when repository throws exception twice but after it no throws then StateFlow should not be killed`() =
        runTest {
            // given
            val input1 = "TEST"
            val input2 = "NO Test 1 "
            val expectedException = Exception("TEST_EXCEPTION_MESSAGE")
            val expected = Error(expectedException)

            whenever(questionsRepository.getQuestionsMatchesSearchInput(input1))
                .thenReturn(flow { throw expectedException })
            whenever(questionsRepository.getQuestionsMatchesSearchInput(input2)).thenReturn(testFlow)

            // when
            launch {
                viewModel.handleInput(input1)
            }.join()
            advanceUntilIdle()

            // then
            val result = (viewModel.searchState.value as Error).e
            assertEquals(expected.e.message, result.message)

            // when
            launch {
                viewModel.handleInput(input2)
                testFlow.emit(expectedList)
            }.join()
            advanceUntilIdle()

            // then
            val result2 = viewModel.questions.value
            assertEquals(expectedList, result2)
        }

}

/*
 * Helpers
 */
private fun createTestList(): List<Question> {
    return listOf(
        Question(
            questionId = 1,
            topicId = 1,
            nameTopic = "TEST_TOPIC_1",
            examId = 1,
            nameExam = "TEST_EXAM_1",
            content = "TEST_CONTENT_1",
            rightAns = "TEST_RIGHT_ANS_1",
            ans1 = "TEST_ANS_1_1",
            ans2 = "TEST_ANS_1_2",
            ans3 = "TEST_ANS_1_3",
        ),
        Question(
            questionId = 2,
            topicId = 2,
            nameTopic = "TEST_TOPIC_2",
            examId = 2,
            nameExam = "TEST_EXAM_2",
            content = "TEST_CONTENT_2",
            rightAns = "TEST_RIGHT_ANS_2",
            ans1 = "TEST_ANS_2_1",
            ans2 = "TEST_ANS_2_2",
            ans3 = "TEST_ANS_2_3",
        )
    )
}
