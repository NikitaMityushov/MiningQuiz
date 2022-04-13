package com.mityushovn.miningquiz.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.database.AppSQLiteHelper
import com.mityushovn.miningquiz.database.attemptExamDao.AttemptExamDao
import com.mityushovn.miningquiz.database.attemptTopicDao.AttemptTopicDao
import com.mityushovn.miningquiz.database.attemptTopicDao.AttemptTopicDaoAPI
import com.mityushovn.miningquiz.database.examDao.ExamDao
import com.mityushovn.miningquiz.database.examDao.ExamDaoAPI
import com.mityushovn.miningquiz.database.questionsDao.QuestionsDao
import com.mityushovn.miningquiz.database.questionsDao.QuestionsDaoAPI
import com.mityushovn.miningquiz.database.topicDao.TopicDao
import com.mityushovn.miningquiz.database.topicDao.TopicDaoAPI
import com.mityushovn.miningquiz.repository.attemptsRepository.AttemptsRepository
import com.mityushovn.miningquiz.repository.attemptsRepository.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.repository.examsRepository.ExamsRepository
import com.mityushovn.miningquiz.repository.examsRepository.ExamsRepositoryAPI
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepository
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.repository.topicsRepository.TopicsRepository
import com.mityushovn.miningquiz.repository.topicsRepository.TopicsRepositoryAPI
import kotlinx.coroutines.Dispatchers

/**
 *
 * Singleton creates database and all Repositories in the application.
 */
object Repositories {
    private lateinit var applicationContext: Context
    private val ioDispatcher = Dispatchers.IO

//     Database and dao
    private val database: SQLiteDatabase by lazy<SQLiteDatabase> {
        AppSQLiteHelper(applicationContext).writableDatabase
    }

    private val questionsDao: QuestionsDaoAPI by lazy {
        QuestionsDao(database)
    }

    private val topicDao: TopicDaoAPI by lazy {
        TopicDao(database)
    }

    private val examDao: ExamDaoAPI by lazy {
        ExamDao(database)
    }

    private val attemptTopicDao: AttemptTopicDaoAPI by lazy {
        AttemptTopicDao(database)
    }

    private val attemptExamDao: AttemptExamDao by lazy {
        AttemptExamDao(database)
    }


    // Repositories
    val questionsRepository: QuestionsRepositoryAPI by lazy {
        QuestionsRepository(
            questionsDao = questionsDao,
            coroutineDispatcher = ioDispatcher
        )
    }

    val attemptsRepository: AttemptsRepositoryAPI by lazy {
        AttemptsRepository(
            attemptExamDao = attemptExamDao,
            attemptTopicDao = attemptTopicDao,
            coroutineDispatcher = ioDispatcher
        )
    }

    val examsRepository: ExamsRepositoryAPI by lazy {
        ExamsRepository(
            examDao = examDao,
            coroutineDispatcher = ioDispatcher
        )
    }

    val topicsRepository: TopicsRepositoryAPI by lazy {
        TopicsRepository(
            topicDao = topicDao,
            coroutineDispatcher = ioDispatcher
        )
    }

    // This method should be called in onCreate() of Application, or on Activity, or Service
    // for initialization database and repositories
    fun init(context: Context) {
        applicationContext = context
    }
}
