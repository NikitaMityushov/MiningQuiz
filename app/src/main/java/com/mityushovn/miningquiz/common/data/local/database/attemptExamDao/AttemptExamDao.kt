package com.mityushovn.miningquiz.common.data.local.database.attemptExamDao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.common.data.local.database.AppSQLiteContract
import com.mityushovn.miningquiz.common.domain.models.AttemptExam
import com.mityushovn.miningquiz.common.utils.toIntForDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Implementation of AttemptExamDaoAPI interface.
 * @see AttemptExamDaoAPI
 */
class AttemptExamDao @Inject constructor(
    private val db: SQLiteDatabase
) : AttemptExamDaoAPI {

    /**
     * @see AttemptExamDaoAPI.insertAttemptExam
     */
    override suspend fun insertAttemptExam(attemptExam: AttemptExam): Flow<Boolean> = flow {
        Timber.d("insertAttemptExam, attemptExam is $attemptExam")
        Timber.d(Thread.currentThread().name)
        try {
            db.execSQL(
                "INSERT INTO attempt_exam(exam_id, passed_at, success) VALUES (${attemptExam.examId}, ${attemptExam.passedAt.time}, ${attemptExam.success.toIntForDB()})"
            )
            emit(true)
        } catch (e: Exception) {
            Timber.e(e)
            emit(false)
        }
    }

    /**
     * @see AttemptExamDaoAPI.deleteAllExamsAttempts
     */
    override suspend fun deleteAllExamsAttempts(): Flow<Boolean> = flow {
        try {
            db.execSQL(AppSQLiteContract.AttemptExamTable.DELETE_ALL_EXAM_ATTEMPTS)
            emit(true)
        } catch (e: Exception) {
            Timber.e(e)
            e.printStackTrace()
            emit(false)
        }
    }

    /**
     * @see AttemptExamDaoAPI.getNumberOfExamSolvingAttempts
     */
    override suspend fun getNumberOfExamSolvingAttempts(): Int {
        // 1) db query
        val cursor = db.rawQuery(
            AppSQLiteContract.AttemptExamTable.GET_NUMBER_OF_ALL_EXAM_SOLVING_ATTEMPTS_QUERY,
            null
        )
        // 2) handle response(cursor)
        return handleCursorInt(cursor)
    }

    /**
     * @see AttemptExamDaoAPI.getNumberOfSuccessfulExamSolvingAttempts
     */
    override suspend fun getNumberOfSuccessfulExamSolvingAttempts(): Int {
        // 1) db query
        val cursor = db.rawQuery(
            AppSQLiteContract.AttemptExamTable.GET_NUMBER_OF_SUCCESS_EXAM_SOLVING_ATTEMPTS_QUERY,
            null
        )
        // 2) handle response(cursor)
        return handleCursorInt(cursor)
    }

    /**
     * @see AttemptExamDaoAPI.getNumberOfFailedExamSolvingAttempts
     */
    override suspend fun getNumberOfFailedExamSolvingAttempts(): Int {
        // 1) db query
        val cursor = db.rawQuery(
            AppSQLiteContract.AttemptExamTable.GET_NUMBER_OF_FAILED_EXAM_SOLVING_ATTEMPTS_QUERY,
            null
        )
        // 2) handle response(cursor)
        return handleCursorInt(cursor)
    }

    /*
     * private methods
     */
    /**
     * Method executes common routine: reads data from Cursor instance, write to List with
     * Question init, and close the Cursor resource.
     */
    private suspend inline fun handleCursorInt(
        cursor: Cursor
    ): Int {
        cursor.use {
            return if (cursor.count == 0) {
                0
            } else {
                cursor.moveToFirst()
                cursor.getInt(cursor.getColumnIndexOrThrow(AppSQLiteContract.Common.NUMBER_OF_ATTEMPTS))
            }
        }
    }
}