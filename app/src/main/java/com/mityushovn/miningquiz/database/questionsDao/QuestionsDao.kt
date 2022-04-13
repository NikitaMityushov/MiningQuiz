package com.mityushovn.miningquiz.database.questionsDao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.database.AppSQLiteContract
import com.mityushovn.miningquiz.models.Question
import com.mityushovn.miningquiz.models.WrongAnswered
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import timber.log.Timber

private val STRING_ARRAY_OF_QUESTION_COLUMNS = arrayOf(
    AppSQLiteContract.QuestionTable.COLUMN_QUESTION_ID,
    AppSQLiteContract.QuestionTable.COLUMN_CONTENT,
    AppSQLiteContract.QuestionTable.COLUMN_RIGHT_ANS,
    AppSQLiteContract.QuestionTable.COLUMN_ANS_1,
    AppSQLiteContract.QuestionTable.COLUMN_ANS_2,
    AppSQLiteContract.QuestionTable.COLUMN_ANS_3,
    AppSQLiteContract.TopicTable.COLUMN_TOPIC_ID,
    AppSQLiteContract.TopicTable.COLUMN_NAME_TOPIC,
    AppSQLiteContract.ExamTable.COLUMN_EXAM_ID,
    AppSQLiteContract.ExamTable.COLUMN_NAME_EXAM
)

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Implementation of QuestionsDaoAPI interface.
 * @see QuestionsDaoAPI
 */
class QuestionsDao(
    private val db: SQLiteDatabase
) : QuestionsDaoAPI {

    /**
     * @see QuestionsDaoAPI.getAllQuestionsWithWrongAnswers
     */
    override suspend fun getAllQuestionsWithWrongAnswers(): Flow<List<Question>> = flow {
        // 1) db query
        val cursor = db.rawQuery(
            AppSQLiteContract.QuestionTable.SELECT_ALL_WRONG_ANSWERED_QUERY,
            STRING_ARRAY_OF_QUESTION_COLUMNS
        )
        // 2) handle response(cursor)
        handleCursor(cursor, this)
    }

    /**
     * @see QuestionsDaoAPI.getAllQuestionsFromTopic
     */
    override suspend fun getAllQuestionsFromTopic(topicId: Int): Flow<List<Question>> = flow {
        Timber.d("getAllQuestionsFromTopic() SQL query = SELECT * FROM \"question_view\" WHERE \"topic_id\" = $topicId;")
        val cursor = db.rawQuery(
            "SELECT * FROM question_view WHERE topic_id = ${topicId};",
            STRING_ARRAY_OF_QUESTION_COLUMNS
        )
        handleCursor(cursor, this)
    }

    /**
     * @see QuestionsDaoAPI.insertWrongAnsweredQuestion
     */
    override suspend fun insertWrongAnsweredQuestion(wrongAnswered: WrongAnswered): Flow<Boolean> =
        flow {
            Timber.d("SQL query = INSERT INTO wrong_answered(question_id, answered_at) VALUES (" + wrongAnswered.questionId + ", " + wrongAnswered.answeredAt + ");")
            db.execSQL(
                "INSERT INTO wrong_answered(question_id, answered_at) VALUES (${wrongAnswered.questionId}, ${wrongAnswered.answeredAt});"
            )
            emit(true)
        }

    /**
     * @see QuestionsDaoAPI.getRandomQuestionsFromExamIdAndNumberOfQuestions
     */
    override suspend fun getRandomQuestionsFromExamIdAndNumberOfQuestions(
        examId: Int,
        numberOfQuestions: Int
    ): Flow<List<Question>> = flow {
        // 1) db query
        val cursor = db.rawQuery(
            "SELECT * FROM ${AppSQLiteContract.QuestionTable.QUESTIONS_VIEW_NAME} WHERE exam_id = $examId ORDER BY random() LIMIT $numberOfQuestions;",
            STRING_ARRAY_OF_QUESTION_COLUMNS
        )
        // 2) handle response(cursor)
        handleCursor(cursor, this)
    }

    /**
     * @see QuestionsDaoAPI.getQuestionsMatchesSearchInput
     */
    override suspend fun getQuestionsMatchesSearchInput(input: String): Flow<List<Question>> =
        flow {
            // 1) db query
            val cursor = db.rawQuery(
                "SELECT * FROM question_view WHERE content LIKE \"%${input}%\";",
                STRING_ARRAY_OF_QUESTION_COLUMNS
            )

            // 2) handle response(cursor)
            handleCursor(cursor, this)
        }

    /*
        private methods
     */

    /**
     * Method executes common routine: reads data from Cursor instance, write to List with
     * Question init, and close the Cursor resource.
     */
    private suspend inline fun handleCursor(
        cursor: Cursor,
        collector: FlowCollector<List<Question>>
    ) {
        cursor.use {
            if (cursor.count == 0) {
                collector.emit(emptyList())
            } else {
                val list = mutableListOf<Question>()
                // 3 iterate through cursor
                while (cursor.moveToNext()) {
                    list.add(
                        Question(
                            questionId = cursor.getInt(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.QuestionTable.COLUMN_QUESTION_ID)
                            ),
                            topicId = cursor.getInt(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.TopicTable.COLUMN_TOPIC_ID)
                            ),
                            nameTopic = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.TopicTable.COLUMN_NAME_TOPIC)
                            ),
                            examId = cursor.getInt(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.ExamTable.COLUMN_EXAM_ID)
                            ),
                            nameExam = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.ExamTable.COLUMN_NAME_EXAM)
                            ),
                            content = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.QuestionTable.COLUMN_CONTENT)
                            ),
                            rightAns = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.QuestionTable.COLUMN_RIGHT_ANS)
                            ),
                            ans1 = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.QuestionTable.COLUMN_ANS_1)
                            ),
                            ans2 = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.QuestionTable.COLUMN_ANS_2)
                            ),
                            ans3 = cursor.getString(
                                cursor.getColumnIndexOrThrow(AppSQLiteContract.QuestionTable.COLUMN_ANS_3)
                            ),
                        )
                    )
                }
                // 3) emit result List<Exam>
                collector.emit(list)
            }
        }
    }

}