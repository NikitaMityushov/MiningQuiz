package com.mityushovn.miningquiz.screens.main.examsfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.models.Exam
import com.mityushovn.miningquiz.repository.Repositories
import com.mityushovn.miningquiz.repository.examsRepository.ExamsRepositoryAPI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class ExamsViewModel(
    private val examsRepository: ExamsRepositoryAPI = Repositories.examsRepository
) : ViewModel() {


    val examsList: MutableLiveData<List<Exam>> = MutableLiveData()

    init {
        Timber.d("Init block")
        updateExamsList()
    }

    private fun updateExamsList() {
        viewModelScope.launch {
            examsRepository.getAllExams().collect {
                Timber.d("UpdateExamsList() list size is ${it.size}")
                examsList.value = it
            }
        }
    }
}