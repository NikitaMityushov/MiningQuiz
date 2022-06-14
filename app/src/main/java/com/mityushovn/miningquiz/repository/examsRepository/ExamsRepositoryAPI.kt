package com.mityushovn.miningquiz.repository.examsRepository

import com.mityushovn.miningquiz.models.domain.Exam
import kotlinx.coroutines.flow.Flow

/**
 * @author Nikita Mityushov 12.04.22
 * @since 1.0
 * Interface for repositories of Exam entity.
 * @see Exam
 */
interface ExamsRepositoryAPI {

    /**
     * @return Flow with List of all Exam class instances.
     * @see Exam
     */
    suspend fun getAllExams(): Flow<List<Exam>>

}