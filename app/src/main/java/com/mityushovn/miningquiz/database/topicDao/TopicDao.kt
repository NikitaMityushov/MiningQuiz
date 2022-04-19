package com.mityushovn.miningquiz.database.topicDao

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.database.AppSQLiteContract
import com.mityushovn.miningquiz.models.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Implementation of TopicDaoAPI interface.
 * @see TopicDaoAPI
 */
class TopicDao(private val db: SQLiteDatabase) : TopicDaoAPI {

    /**
     * @see TopicDaoAPI.getAllTopics
     */
    override suspend fun getAllTopics(): Flow<List<Topic>> = flow {
        // 1) db query
        val cursor = db.rawQuery(
            AppSQLiteContract.TopicTable.SELECT_ALL_TOPICS_QUERY,
            null
        )
        // 2) handle response(cursor)
        cursor.use {
            if (cursor.count == 0) {
                throw Exception("There are no topics in the db")
            } else {
                val list = mutableListOf<Topic>()
                // 3 iterate through cursor
                while (cursor.moveToNext()) {
                    list.add(
                        Topic(
                            topicId = cursor.getInt(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.TopicTable.COLUMN_TOPIC_ID)
                            ),
                            nameTopic = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.TopicTable.COLUMN_NAME_TOPIC)
                            ),
                            nameExam = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.ExamTable.COLUMN_NAME_EXAM)
                            )
                        )
                    )
                }
                // 3) emit result List<Topic>
                emit(list)
            }
        }
    }

}