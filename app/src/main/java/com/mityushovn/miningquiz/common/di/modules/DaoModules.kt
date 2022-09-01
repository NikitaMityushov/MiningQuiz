package com.mityushovn.miningquiz.common.di.modules

import com.mityushovn.miningquiz.common.data.local.database.attemptExamDao.AttemptExamDao
import com.mityushovn.miningquiz.common.data.local.database.attemptExamDao.AttemptExamDaoAPI
import com.mityushovn.miningquiz.common.data.local.database.attemptTopicDao.AttemptTopicDao
import com.mityushovn.miningquiz.common.data.local.database.attemptTopicDao.AttemptTopicDaoAPI
import com.mityushovn.miningquiz.common.data.local.database.examDao.ExamDao
import com.mityushovn.miningquiz.common.data.local.database.examDao.ExamDaoAPI
import com.mityushovn.miningquiz.common.data.local.database.questionsDao.QuestionsDao
import com.mityushovn.miningquiz.common.data.local.database.questionsDao.QuestionsDaoAPI
import com.mityushovn.miningquiz.common.data.local.database.topicDao.TopicDao
import com.mityushovn.miningquiz.common.data.local.database.topicDao.TopicDaoAPI
import dagger.Binds
import dagger.Module

@Module
interface AppDaoModule {

    @Binds
    fun bindAttemptExamDao(attemptExamDao: AttemptExamDao): AttemptExamDaoAPI

    @Binds
    fun bindAttemptTopicDao(attemptTopicDao: AttemptTopicDao): AttemptTopicDaoAPI

    @Binds
    fun bindQuestionsDao(questionsDao: QuestionsDao): QuestionsDaoAPI
}

@Module
interface MainActDaoModule {
    @Binds
    fun bindExamDaoAPI(examDao: ExamDao): ExamDaoAPI

    @Binds
    fun bindTopicDaoAPI(topicDao: TopicDao): TopicDaoAPI
}

