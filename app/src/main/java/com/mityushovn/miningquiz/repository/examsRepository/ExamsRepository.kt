package com.mityushovn.miningquiz.repository.examsRepository

import com.mityushovn.miningquiz.database.examDao.ExamDaoAPI
import com.mityushovn.miningquiz.di.qualifiers.RepositoryCoroutineDispatcher
import com.mityushovn.miningquiz.di.scopes.AppScope
import com.mityushovn.miningquiz.di.scopes.MainActivityScope
import com.mityushovn.miningquiz.models.domain.Exam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author Nikita Mityushov 12.04.22
 * @since 1.0
 * ExamsRepositoryAPI implementation.
 * @see ExamsRepositoryAPI
 */
@MainActivityScope
class ExamsRepository @Inject constructor(
    val examDao: ExamDaoAPI,
    @RepositoryCoroutineDispatcher
    val coroutineDispatcher: CoroutineDispatcher
) : ExamsRepositoryAPI {

    /**
     * @see ExamsRepositoryAPI.getAllExams
     */
    override suspend fun getAllExams(): Flow<List<Exam>> {
        return examDao.getAllExams().flowOn(coroutineDispatcher)
    }
}