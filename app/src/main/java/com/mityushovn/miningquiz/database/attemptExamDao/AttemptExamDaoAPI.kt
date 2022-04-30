package com.mityushovn.miningquiz.database.attemptExamDao

import com.mityushovn.miningquiz.models.AttemptExam
import kotlinx.coroutines.flow.Flow

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Interface for the attempt_topic table access.
 */
interface AttemptExamDaoAPI {

    /**
     * Inserts an attempt of Exam solving to the storage.
     * @param attemptExam is an instance of AttemptExam class.
     * @see AttemptExam
     * @return Flow with a boolean value that indicates success of the operation.
     */
    suspend fun insertAttemptExam(attemptExam: AttemptExam): Flow<Boolean>

    /**
     * Deletes all exams attempts from database.
     */
    suspend fun deleteAllExamsAttempts(): Flow<Boolean>

    /**
     * @return number of all solved exams.
     */
    suspend fun getNumberOfExamSolvingAttempts(): Int

    /**
     * @return number of successful solved exams.
     */
    suspend fun getNumberOfSuccessfulExamSolvingAttempts(): Int

    /**
     * @return number of failed exams.
     */
    suspend fun getNumberOfFailedExamSolvingAttempts(): Int

}