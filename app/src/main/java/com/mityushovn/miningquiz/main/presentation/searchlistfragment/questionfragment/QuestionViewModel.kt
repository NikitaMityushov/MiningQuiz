package com.mityushovn.miningquiz.main.presentation.searchlistfragment.questionfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.common.domain.models.Question
import com.mityushovn.miningquiz.common.domain.repositories.QuestionsRepositoryAPI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Nikita Mityushov 19.04.22
 * @since 1.0
 * ViewModel class to store state for a QuestionsFragment.
 * @property question is observable LiveData<Question> source for storing Question instance.
 * @see QuestionFragment
 * @see Question
 */
class QuestionViewModel(
    private val questionId: Int,
    private val questionsRepository: QuestionsRepositoryAPI
) : ViewModel() {

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    init {
        Timber.d("Init block, questionId is $questionId")
        updateQuestion()
    }

    /**
     * Launch a new Coroutines Job in a viewModelScope that sends request to a QuestionRepository,
     * and after emitting response updates question LiveData property.
     * @see QuestionsRepositoryAPI
     */
    private fun updateQuestion() {
        viewModelScope.launch {
            questionsRepository.getQuestionById(questionId)
                .collect {
                    _question.value = it
                }
        }
    }
}