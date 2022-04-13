package com.mityushovn.miningquiz.database.examDao

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.database.AppSQLiteContract
import com.mityushovn.miningquiz.models.Exam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Implementation of ExamDaoAPI interface.
 * @see ExamDaoAPI
 */
class ExamDao(
    private val db: SQLiteDatabase
) : ExamDaoAPI {

    /**
     * @see ExamDaoAPI.getAllExams
     */
    override suspend fun getAllExams(): Flow<List<Exam>> = flow {
        // 1) db query
        val cursor = db.query(
            AppSQLiteContract.ExamTable.TABLE_NAME,
            arrayOf(
                AppSQLiteContract.ExamTable.COLUMN_EXAM_ID,
                AppSQLiteContract.ExamTable.COLUMN_NAME_EXAM
            ),
            null, null, null, null, null
        )
        // 2) handle response(cursor)
        cursor.use {
            if (cursor.count == 0) {
                throw Exception("There are no exams in the db")
            } else {
                val list = mutableListOf<Exam>()
                // 3 iterate through cursor
                while (cursor.moveToNext()) {
                    list.add(
                        Exam(
                            examId = cursor.getInt(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.ExamTable.COLUMN_EXAM_ID)
                            ),
                            nameExam = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.ExamTable.COLUMN_NAME_EXAM)
                            )
                        )
                    )
                }
                // 3) emit result List<Exam>
                emit(list)
            }
        }
    }

}