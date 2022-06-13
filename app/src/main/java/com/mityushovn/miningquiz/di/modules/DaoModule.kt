package com.mityushovn.miningquiz.di.modules

import com.mityushovn.miningquiz.database.attemptExamDao.AttemptExamDao
import com.mityushovn.miningquiz.database.attemptExamDao.AttemptExamDaoAPI
import com.mityushovn.miningquiz.database.attemptTopicDao.AttemptTopicDao
import com.mityushovn.miningquiz.database.attemptTopicDao.AttemptTopicDaoAPI
import com.mityushovn.miningquiz.database.examDao.ExamDao
import com.mityushovn.miningquiz.database.examDao.ExamDaoAPI
import com.mityushovn.miningquiz.database.questionsDao.QuestionsDao
import com.mityushovn.miningquiz.database.questionsDao.QuestionsDaoAPI
import com.mityushovn.miningquiz.database.topicDao.TopicDao
import com.mityushovn.miningquiz.database.topicDao.TopicDaoAPI
import dagger.Binds
import dagger.Module

@Module
interface DaoModule {

    @Binds
    fun bindExamDaoAPI(examDao: ExamDao): ExamDaoAPI

    @Binds
    fun bindTopicDaoAPI(topicDao: TopicDao): TopicDaoAPI

    @Binds
    fun bindAttemptExamDao(attemptExamDao: AttemptExamDao): AttemptExamDaoAPI

    @Binds
    fun bindAttemptTopicDao(attemptTopicDao: AttemptTopicDao): AttemptTopicDaoAPI

    @Binds
    fun bindQuestionsDao(questionsDao: QuestionsDao): QuestionsDaoAPI
}