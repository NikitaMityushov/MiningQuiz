package com.mityushovn.miningquiz.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.models.Question
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepositoryAPI
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import com.mityushovn.miningquiz.screens.main.mainfragment.MainFragment
import com.mityushovn.miningquiz.screens.main.searchlistfragment.SearchListFragment

private const val TEXT_ENTERED_DEBOUNCE_MILLIS = 1500L

/**
 * @author Nikita Mityushov
 * @since 1.0
 * ViewModel class for MainActivity, MainFragment and SearchListFragment to store state.
 * @property questions is observable LiveData<List<Question>> source for storing List of
 * Question instances.
 * @see Question
 * @see MainActivity
 * @see MainFragment
 * @see SearchListFragment
 */
class MainActivityViewModel(
    private val questionsRepository: QuestionsRepositoryAPI
) : ViewModel() {

    private val _questions =
        MutableLiveData<List<Question>>(emptyList()) // init with empty list is essential for data binding
    val questions: LiveData<List<Question>>
        get() = _questions

    /*
        state of loading
     */
    private val _searchState = MutableLiveData<SearchState>(Ready)
    val searchState: LiveData<SearchState>
        get() = _searchState

    /*
        for searching
     */
    private val queryFlow = MutableStateFlow("")


    init {
        Timber.d("Init block")
        viewModelScope.launch {
            queryFlow
                .sample(TEXT_ENTERED_DEBOUNCE_MILLIS)
                .onEach {
                    _searchState.value = Loading
                }
                .mapLatest(::sendSearchRequest)
                .collect {
                    _searchState.value = Ready
                }
        }
    }

    fun handleInput(input: String) {
        queryFlow.value = input
    }

    /*
        private methods
     */
    private suspend fun sendSearchRequest(input: String) {
        Timber.d("Search input is: $input")
        if (input.isNotEmpty()) {
            questionsRepository.getQuestionsMatchesSearchInput(input).collect {
                // debug
                it.forEach { q ->
                    Timber.d(q.content)
                }
                // end debug
                _questions.value = it
            }
        }
        Timber.d("Input is empty")
    }

}