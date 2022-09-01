package com.mityushovn.miningquiz.common.data.local.database.examDao

import com.mityushovn.miningquiz.common.domain.models.Exam
import kotlinx.coroutines.flow.Flow

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Interface for the exam table access.
 */
interface ExamDaoAPI {

    /**
     * @return Flow with List of all Exam class instances.
     * @see Exam
     */
    suspend fun getAllExams(): Flow<List<Exam>>
}