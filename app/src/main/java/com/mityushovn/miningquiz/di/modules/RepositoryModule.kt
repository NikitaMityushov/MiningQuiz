package com.mityushovn.miningquiz.di.modules

import com.mityushovn.miningquiz.repository.attemptsRepository.AttemptsRepository
import com.mityushovn.miningquiz.repository.attemptsRepository.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.repository.examsRepository.ExamsRepository
import com.mityushovn.miningquiz.repository.examsRepository.ExamsRepositoryAPI
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepository
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.repository.topicsRepository.TopicsRepository
import com.mityushovn.miningquiz.repository.topicsRepository.TopicsRepositoryAPI
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindQuestionRepository(questionsRepository: QuestionsRepository): QuestionsRepositoryAPI

    @Binds
    fun bindExamsRepository(examsRepository: ExamsRepository): ExamsRepositoryAPI

    @Binds
    fun bindTopicsRepository(topicsRepository: TopicsRepository): TopicsRepositoryAPI

    @Binds
    fun bindAttemptsRepository(attemptsRepository: AttemptsRepository): AttemptsRepositoryAPI
}
