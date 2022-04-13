package com.mityushovn.miningquiz.repository.examsRepository

import com.mityushovn.miningquiz.database.examDao.ExamDaoAPI
import com.mityushovn.miningquiz.models.Exam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author Nikita Mityushov 12.04.22
 * @since 1.0
 * ExamsRepositoryAPI implementation.
 * @see ExamsRepositoryAPI
 */
class ExamsRepository(
    private val examDao: ExamDaoAPI,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ExamsRepositoryAPI {

    /**
     * @see ExamsRepositoryAPI.getAllExams
     */
    override suspend fun getAllExams(): Flow<List<Exam>> {
        return examDao.getAllExams().flowOn(coroutineDispatcher)
    }
}