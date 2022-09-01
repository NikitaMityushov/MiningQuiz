package com.mityushovn.miningquiz.common.data.local.repositories.examsRepository

import com.mityushovn.miningquiz.common.data.local.database.examDao.ExamDaoAPI
import com.mityushovn.miningquiz.common.di.qualifiers.RepositoryCoroutineDispatcher
import com.mityushovn.miningquiz.common.di.scopes.MainActivityScope
import com.mityushovn.miningquiz.common.domain.models.Exam
import com.mityushovn.miningquiz.common.domain.repositories.ExamsRepositoryAPI
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