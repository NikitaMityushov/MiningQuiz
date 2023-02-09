package com.mityushovn.miningquiz.data_attempts.internal.attemptTopicDao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.models.AttemptTopic
import com.mityushovn.miningquiz.data_attempts.internal.data.AppSQLiteContract
import com.mityushovn.miningquiz.core_utils.toIntForDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Implementation of AttemptTopicDaoAPI interface.
 * @see AttemptTopic
 */
internal class AttemptTopicDao(
    private val db: SQLiteDatabase
) : AttemptTopicDaoAPI {

    /**
     * @see AttemptTopicDaoAPI.insertAttemptTopic
     */
    override suspend fun insertAttemptTopic(attemptTopic: AttemptTopic): Flow<Boolean> = flow {
        try {
            db.execSQL(
                "INSERT INTO attempt_topic(topic_id, passed_at, success) VALUES (${attemptTopic.topicId}, ${attemptTopic.passedAt.time}, ${attemptTopic.success.toIntForDB()})"
            )
            emit(true)
        } catch (e: Exception) {
            Timber.e(e)
            emit(false)
        }
    }

    /**
     * @see AttemptTopicDaoAPI.deleteAllTopicAttempts
     */
    override suspend fun deleteAllTopicAttempts(): Flow<Boolean> = flow {
        try {
            db.execSQL(AppSQLiteContract.AttemptTopicTable.DELETE_ALL_TOPIC_ATTEMPTS)
            emit(true)
        } catch (e: Exception) {
            Timber.e(e)
            e.printStackTrace()
            emit(false)
        }
    }

    /**
     * @see AttemptTopicDaoAPI.getNumberOfTopicSolvingAttempts
     */
    override suspend fun getNumberOfTopicSolvingAttempts(): Int {
        // 1) db query
        val cursor = db.rawQuery(
            AppSQLiteContract.AttemptTopicTable.GET_NUMBER_OF_ALL_TOPICS_SOLVING_ATTEMPTS_QUERY,
            null
        )
        // 2) handle response(cursor)
        return handleCursorInt(cursor)
    }

    /**
     * @see AttemptTopicDaoAPI.getNumberOfSuccessfulTopicSolvingAttempts
     */
    override suspend fun getNumberOfSuccessfulTopicSolvingAttempts(): Int {
        // 1) db query
        val cursor = db.rawQuery(
            AppSQLiteContract.AttemptTopicTable.GET_NUMBER_OF_SUCCESS_TOPIC_SOLVING_ATTEMPTS_QUERY,
            null
        )
        // 2) handle response(cursor)
        return handleCursorInt(cursor)
    }

    /**
     * @see AttemptTopicDaoAPI.getNumberOfFailedTopicSolvingAttempts
     */
    override suspend fun getNumberOfFailedTopicSolvingAttempts(): Int {
        // 1) db query
        val cursor = db.rawQuery(
            AppSQLiteContract.AttemptTopicTable.GET_NUMBER_OF_FAILED_TOPIC_SOLVING_ATTEMPTS_QUERY,
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