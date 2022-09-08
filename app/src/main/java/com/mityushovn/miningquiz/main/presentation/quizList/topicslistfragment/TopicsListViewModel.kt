package com.mityushovn.miningquiz.main.presentation.quizList.topicslistfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.common.domain.models.Topic
import com.mityushovn.miningquiz.common.domain.repositories.TopicsRepositoryAPI
import com.mityushovn.miningquiz.common.utils.*
import com.mityushovn.miningquiz.main.presentation.activity.Loading
import com.mityushovn.miningquiz.main.presentation.activity.Ready
import com.mityushovn.miningquiz.main.presentation.activity.SearchState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val RETRIES_NUMBER = 1L

/**
 * @author Nikita Mityushov
 * @since 1.0
 * ViewModel class for TopicsListFragment to store state.
 * @property topics is observable LiveData<List<Topic>> source for storing List of Topic instances.
 * @see Topic
 * @see TopicsListFragment
 */
class TopicsListViewModel(
    private val topicsRepository: TopicsRepositoryAPI
) : ViewModel() {

    private val _topics = MutableStateFlow<List<Topic>>(emptyList())
    val topics: StateFlow<List<Topic>>
        get() = _topics

    /*
        state of loading
    */
    private val _loadingState = MutableStateFlow<SearchState>(Ready)
    val loadingState: StateFlow<SearchState>
        get() = _loadingState

    /*
        error event
     */
    private val _showLoadingTopicsErrorMessage: MutableLiveEvent<String> =
        MutableStateFlow(Event(""))
    val showLoadingTopicsErrorMessage: LiveEvent<String> = _showLoadingTopicsErrorMessage.share()

    init {
        updateTopicsList()
    }

    /**
     * Launch a new Coroutines Job in a viewModelScope that sends request to a TopicsRepository,
     * and after emitting response updates exam LiveData property.
     * @see TopicsRepositoryAPI
     */
    fun updateTopicsList() {
        viewModelScope.launch {
            customRetryFlow(RETRIES_NUMBER) {
                topicsRepository.getAllTopics()
            }.catch {
                _showLoadingTopicsErrorMessage.value = Event(it.message ?: "")
                _loadingState.value = Ready
                _topics.value = emptyList()
            }.onStart {
                _loadingState.value = Loading
            }.collect {
                _topics.value = it
                _loadingState.value = Ready
            }
        }
    }
}