package com.mityushovn.miningquiz.screens.main.quizList.topicslistfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.models.Exam
import com.mityushovn.miningquiz.models.Topic
import com.mityushovn.miningquiz.repository.examsRepository.ExamsRepositoryAPI
import com.mityushovn.miningquiz.repository.topicsRepository.TopicsRepositoryAPI
import com.mityushovn.miningquiz.screens.main.quizList.examsfragment.ExamsFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

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

    private val _topics = MutableLiveData<List<Topic>>(emptyList())
    val topics: LiveData<List<Topic>>
        get() = _topics

    init {
        Timber.d("Init block")
        updateTopicsList()
    }

    /**
     * Launch a new Coroutines Job in a viewModelScope that sends request to a TopicsRepository,
     * and after emitting response updates exam LiveData property.
     * @see TopicsRepositoryAPI
     */
    private fun updateTopicsList() {
        viewModelScope.launch {
            topicsRepository.getAllTopics().collect {
                _topics.value = it
            }
        }
    }
}