package com.mityushovn.miningquiz.common.di.modules

import com.mityushovn.miningquiz.common.data.local.repositories.attemptsRepository.AttemptsRepository
import com.mityushovn.miningquiz.common.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.common.data.local.repositories.examsRepository.ExamsRepository
import com.mityushovn.miningquiz.common.domain.repositories.ExamsRepositoryAPI
import com.mityushovn.miningquiz.common.data.local.repositories.questionsRepository.QuestionsRepository
import com.mityushovn.miningquiz.common.domain.repositories.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.common.data.local.repositories.topicsRepository.TopicsRepository
import com.mityushovn.miningquiz.common.domain.repositories.TopicsRepositoryAPI
import dagger.Binds
import dagger.Module


@Module
interface AppRepositoryModule {

    @Binds
    fun bindQuestionRepository(questionsRepository: QuestionsRepository): QuestionsRepositoryAPI

    @Binds
    fun bindAttemptsRepository(attemptsRepository: AttemptsRepository): AttemptsRepositoryAPI
}


@Module
interface MainActRepositoryModule {
    @Binds
    fun bindExamsRepository(examsRepository: ExamsRepository): ExamsRepositoryAPI

    @Binds
    fun bindTopicsRepository(topicsRepository: TopicsRepository): TopicsRepositoryAPI
}
