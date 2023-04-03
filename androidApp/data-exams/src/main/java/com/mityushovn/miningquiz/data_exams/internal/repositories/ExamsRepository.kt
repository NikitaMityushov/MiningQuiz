package com.mityushovn.miningquiz.data_exams.internal.repositories

import com.mityushovn.miningquiz.core_domain.domain.models.Exam
import com.mityushovn.miningquiz.core_domain.domain.repositories.ExamsRepositoryAPI
import com.mityushovn.miningquiz.data_exams.internal.examDao.ExamDaoAPI
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
internal class ExamsRepository(
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