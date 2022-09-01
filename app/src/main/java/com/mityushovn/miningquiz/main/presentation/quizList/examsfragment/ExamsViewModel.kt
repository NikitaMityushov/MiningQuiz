package com.mityushovn.miningquiz.main.presentation.quizList.examsfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.common.domain.models.Exam
import com.mityushovn.miningquiz.common.domain.repositories.ExamsRepositoryAPI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

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

    private val _exams = MutableLiveData<List<Exam>>(emptyList())
    val exams: LiveData<List<Exam>>
        get() = _exams

    init {
        Timber.d("Init block")
        updateExamsList()
    }

    /**
     * Launch a new Coroutines Job in a viewModelScope that sends request to a ExamsRepository,
     * and after emitting response updates exam LiveData property.
     * @see ExamsRepositoryAPI
     */
    private fun updateExamsList() {
        viewModelScope.launch {
            examsRepository.getAllExams().collect {
                _exams.value = it
            }
        }
    }
}