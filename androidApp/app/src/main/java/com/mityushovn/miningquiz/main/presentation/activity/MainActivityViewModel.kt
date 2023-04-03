package com.mityushovn.miningquiz.main.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.core_domain.domain.models.Error
import com.mityushovn.miningquiz.core_domain.domain.models.Loading
import com.mityushovn.miningquiz.core_domain.domain.models.Question
import com.mityushovn.miningquiz.core_domain.domain.models.Ready
import com.mityushovn.miningquiz.core_domain.domain.models.SearchState
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.mityushovn.miningquiz.main.presentation.mainfragment.MainFragment
import com.mityushovn.miningquiz.main.presentation.searchlistfragment.SearchListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

private const val TEXT_ENTERED_DEBOUNCE_MILLIS = 600L
private const val RETRIES_NUMBER = 1L

/**
 * @author Nikita Mityushov
 * @since 1.0
 * ViewModel class for MainActivity, MainFragment and SearchListFragment to store state.
 * @property questions is observable StateFlow<List<Question>> source for storing List of
 * Question instances.
 * @see Question
 * @see MainActivity
 * @see MainFragment
 * @see SearchListFragment
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainActivityViewModel(
    private val questionsRepository: QuestionsRepositoryAPI
) : ViewModel() {

    private val _questions =
        MutableStateFlow<List<Question>>(emptyList()) // init with empty list is essential for data binding
    val questions: StateFlow<List<Question>>
        get() = _questions

    /*
        state of loading
     */
    private val _searchState = MutableStateFlow<SearchState>(Ready)
    val searchState: StateFlow<SearchState>
        get() = _searchState

    /*
        for searching
     */
    var queryFlow = MutableStateFlow("")
        private set

    init {
        collectQueryFlow()
    }

    fun handleInput(input: String) {
        queryFlow.value = input
    }

    /*
        private methods
     */
    private suspend fun sendSearchRequest(input: String): Flow<List<Question>> =
        questionsRepository.getQuestionsMatchesSearchInput(input)

    private fun collectQueryFlow() {
        viewModelScope.launch {
            queryFlow
                .debounce(TEXT_ENTERED_DEBOUNCE_MILLIS)
                .filter { it.isNotBlank() }
//                .onEach { _searchState.value = Loading }
                .flatMapLatest { query ->
                    sendSearchRequest(query)
                        .onEach { _searchState.value = Loading }
                }
                .distinctUntilChanged()
                .retry(RETRIES_NUMBER)
                .catch {
                    _searchState.value = Error(it)
                    _questions.value = emptyList()
                    queryFlow = MutableStateFlow("")
                    collectQueryFlow()
                }
                .collect {
                    _questions.value = it
                    _searchState.value = Ready
                }
        }
    }

}