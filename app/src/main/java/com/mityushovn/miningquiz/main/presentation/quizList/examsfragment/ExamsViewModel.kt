package com.mityushovn.miningquiz.main.presentation.quizList.examsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.common.domain.models.Exam
import com.mityushovn.miningquiz.common.domain.repositories.ExamsRepositoryAPI
import com.mityushovn.miningquiz.common.utils.*
import com.mityushovn.miningquiz.main.presentation.activity.Loading
import com.mityushovn.miningquiz.main.presentation.activity.Ready
import com.mityushovn.miningquiz.main.presentation.activity.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

private const val RETRIES_NUMBER = 1L

/**
 * @author Nikita Mityushov
 * @since 1.0
 * ViewModel class for ExamsFragment to store state.
 * @property exams is observable LiveData<List<Exam>> source for storing List of Exam instances.
 * @see Exam
 * @see ExamsFragment
 */
class ExamsViewModel(
    private val examsRepository: ExamsRepositoryAPI
) : ViewModel() {

    private val _exams = MutableStateFlow(emptyList<Exam>())
    val exams: StateFlow<List<Exam>>
        get() = _exams

    /*
    state of loading
    */
    private val _loadingState = MutableStateFlow<SearchState>(Ready)
    val loadingState: StateFlow<SearchState>
        get() = _loadingState

    /*
        error event
     */
    private val _showLoadingExamsErrorMessage: MutableLiveEvent<String> =
        MutableStateFlow(Event(""))
    val showLoadingExamsErrorMessage: LiveEvent<String> = _showLoadingExamsErrorMessage.share()

    init {
        updateExamsList()
    }

    /**
     * Launch a new Coroutines Job in a viewModelScope that sends request to a ExamsRepository,
     * and after emitting response updates exam LiveData property.
     * @see ExamsRepositoryAPI
     */
    fun updateExamsList() {
        viewModelScope.launch {
            customRetryFlow(RETRIES_NUMBER) { examsRepository.getAllExams() }
                .catch {
                    _showLoadingExamsErrorMessage.value = Event(it.message ?: "")
                    _loadingState.value = Ready
                    _exams.value = emptyList()
                }
                .onStart {
                    _loadingState.value = Loading
                }
                .collect {
                    _exams.value = it
                    _loadingState.value = Ready
                }
        }
    }

}