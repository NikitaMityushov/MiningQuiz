package com.mityushovn.miningquiz.main.presentation.quizList.topicslistfragment

import com.mityushovn.miningquiz.core_domain.domain.models.Topic
import com.mityushovn.miningquiz.debug.MainDispatcherRule
import com.mityushovn.miningquiz.core_domain.domain.repositories.TopicsRepositoryAPI
import com.mityushovn.miningquiz.debug.CoroutineSubject
import com.mityushovn.miningquiz.core_domain.domain.models.Loading
import com.mityushovn.miningquiz.core_domain.domain.models.Ready
import com.mityushovn.quizlist_feature.internal.presentation.topicslistfragment.TopicsListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class TopicsListViewModelTest {

    lateinit var topicsRepositoryAPI: TopicsRepositoryAPI
    lateinit var viewModel: TopicsListViewModel
    lateinit var testTopicsList: List<Topic>
    lateinit var testFlow: MutableStateFlow<List<Topic>>

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    @Before
    fun setUp() = runBlocking {
        topicsRepositoryAPI = mock()
        testTopicsList = createTestTopicsList()
        testFlow = MutableStateFlow(emptyList())
    }

    @Test
    fun `when view model is created, then initially topics list is empty`() {
        viewModel = TopicsListViewModel(
            topicsRepositoryAPI
        )
        assertEquals(emptyList<Topic>(), viewModel.topics.value)
    }

    @Test
    fun `when view model is created, then repository getAllTopics is called`() = runTest {
        // given
        whenever(topicsRepositoryAPI.getAllTopics()).thenReturn(testFlow)
        // when
        launch {
            viewModel =
                TopicsListViewModel(
                    topicsRepositoryAPI
                )
        }.join()
        advanceUntilIdle()
        // then
        verify(topicsRepositoryAPI, times(1)).getAllTopics()
    }

    @Test
    fun `when repository getAllTopics flow throws Exception then request retries one more time and if successfully returns testList`() =
        runTest {
            // given
            whenever(topicsRepositoryAPI.getAllTopics())
                .thenReturn(flow { throw Exception() })
                .thenReturn(flow { emit(testTopicsList) })
            // when
            launch {
                viewModel =
                    TopicsListViewModel(
                        topicsRepositoryAPI
                    )
            }.join()
            advanceUntilIdle()
            // then
            verify(topicsRepositoryAPI, times(2)).getAllTopics()
            assertEquals(testTopicsList, viewModel.topics.value)

        }

    @Test
    fun `when repository getAllTopics flow throws Exception then error event is shown and loading state is Ready`() =
        runTest {
            val errorMessage = "Error right now"
            // given
            whenever(topicsRepositoryAPI.getAllTopics())
                .thenReturn(flow { throw Exception(errorMessage) })
            // when
            launch {
                viewModel =
                    TopicsListViewModel(
                        topicsRepositoryAPI
                    )
            }.join()
            advanceUntilIdle()
            // then
            assertEquals(errorMessage, viewModel.showLoadingTopicsErrorMessage.value.get())
            assertEquals(Ready, viewModel.loadingState.value)
            assertEquals(emptyList<Topic>(), viewModel.topics.value)
        }

    @Test
    fun `when init viewModel then loading state is Loading and after successful result state is Ready`() =
        runTest {
            // given
            val subject = CoroutineSubject<List<Topic>>()
            whenever(topicsRepositoryAPI.getAllTopics()).thenReturn(flow { emit(subject.get()) })
            // when
            launch {
                viewModel =
                    TopicsListViewModel(
                        topicsRepositoryAPI
                    )
            }.join()
            advanceUntilIdle()
            // then
            assertEquals(Loading, viewModel.loadingState.value)
            // when
            subject.sendSuccess(testTopicsList)
            // then
            assertEquals(Ready, viewModel.loadingState.value)
            assertEquals(testTopicsList, viewModel.topics.value)
        }

}

private fun createTestTopicsList(): List<Topic> =
    listOf(
        Topic(1, "Topic 1", "Name 1"),
        Topic(2, "Topic 2", "Name 2"),
        Topic(3, "Topic 3", "Name 3")
    )
