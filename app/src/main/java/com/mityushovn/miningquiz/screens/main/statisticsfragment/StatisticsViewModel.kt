package com.mityushovn.miningquiz.screens.main.statisticsfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.models.AttemptExam
import com.mityushovn.miningquiz.DI.Repositories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class StatisticsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val liveData = MutableLiveData<Boolean>()

    private val stateFlow = MutableStateFlow<Boolean>(false)

    fun refresh() {
        with(viewModelScope) {
            launch {
                Repositories.attemptsRepository.insertAttemptExam(AttemptExam(1, 1, Date(), true))
                    .collect {
                        liveData.value = it
                    }
            }
        }

    }
}

