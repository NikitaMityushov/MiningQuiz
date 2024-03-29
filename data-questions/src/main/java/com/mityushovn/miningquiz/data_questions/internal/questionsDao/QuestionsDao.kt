package com.mityushovn.miningquiz.data_questions.internal.questionsDao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.core_domain.domain.models.Question
import com.mityushovn.miningquiz.core_domain.domain.models.WrongAnswered
import com.mityushovn.miningquiz.data_questions.internal.AppSQLiteContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.*

/**
 * @author Nikita Mityushov 8.04.22
 * @since 1.0
 * Implementation of QuestionsDaoAPI interface.
 * @see QuestionsDaoAPI
 */
internal class QuestionsDao(
    private val db: SQLiteDatabase
) : QuestionsDaoAPI {

    /**
     * @see QuestionsDaoAPI.getAllQuestionsWithWrongAnswers
     */
    override suspend fun getAllQuestionsWithWrongAnswers(): Flow<Deque<Question>> = flow {
        // 1) db query
        val cursor = db.rawQuery(
            AppSQLiteContract.QuestionTable.SELECT_ALL_WRONG_ANSWERED_QUERY,
            null
        )
        // 2) handle response(cursor)
        handleCursorForDeque(cursor, this)
    }

    /**
     * @see QuestionsDaoAPI.getAllQuestionsFromTopic
     */
    // TODO: 14.04.2022 испытать selection args, снести основной запрос в const val. Должно быть where topic_id =? , selection args = new String[] {topicID.toString()};
    override suspend fun getAllQuestionsFromTopic(topicId: Int): Flow<Deque<Question>> = flow {
        Timber.d("getAllQuestionsFromTopic() SQL query = SELECT * FROM \"question_view\" WHERE \"topic_id\" = $topicId;")
        val cursor = db.rawQuery(
            "SELECT * FROM question_view WHERE topic_id = $topicId",
            null
        )
        handleCursorForDeque(cursor, this)
    }

    /**
     * @see QuestionsDaoAPI.insertWrongAnsweredQuestion
     */
    override suspend fun insertWrongAnsweredQuestion(wrongAnswered: WrongAnswered): Flow<Boolean> =
        flow {
            Timber.d("SQL query = INSERT INTO wrong_answered(question_id, answered_at) VALUES (" + wrongAnswered.questionId + ", " + wrongAnswered.answeredAt + ");")
            db.execSQL(
                "INSERT INTO wrong_answered(question_id, answered_at) VALUES (${wrongAnswered.questionId}, ${wrongAnswered.answeredAt})"
            )
            emit(true)
        }

    /**
     * @see QuestionsDaoAPI.getRandomQuestionsFromExamIdAndNumberOfQuestions
     */
    override suspend fun getRandomQuestionsFromExamIdAndNumberOfQuestions(
        examId: Int,
        numberOfQuestions: Int
    ): Flow<Deque<Question>> = flow {
        // 1) db query
        val cursor = db.rawQuery(
            "SELECT * FROM ${AppSQLiteContract.QuestionTable.QUESTIONS_VIEW_NAME} WHERE exam_id = $examId ORDER BY random() LIMIT $numberOfQuestions",
            null
        )
        // 2) handle response(cursor)
        handleCursorForDeque(cursor, this)
    }

    /**
     * @see QuestionsDaoAPI.getQuestionsMatchesSearchInput
     */
    override suspend fun getQuestionsMatchesSearchInput(input: String): Flow<List<Question>> =
        flow {
            // 1) db query
            val cursor = db.rawQuery(
                "SELECT * FROM question_view WHERE content LIKE \"%${input}%\"",
                null
            )

            // 2) handle response(cursor)
            handleCursor(cursor, this)
        }

    /**
     * @see QuestionsDaoAPI.getQuestionById
     */
    override suspend fun getQuestionById(questionId: Int): Flow<Question> = flow {
        // 1) db query
        val cursor = db.rawQuery(
            "SELECT * FROM question_view WHERE question_id = $questionId",
            null
        )
        // 2) handle response(cursor)
        cursor.use {
            if (cursor.count == 0) {
                Timber.e("Question with specified questionId = $questionId must exists, it's illegal state")
                throw IllegalStateException("Question with specified questionId = $questionId must exists")
            } else {
                // 3 iterate through cursor and emit Question
                while (cursor.moveToFirst()) {
                    this.emit(
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
            }
        }
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

    /**
     * Method executes common routine: reads data from Cursor instance, write to Deque with
     * Question init, and close the Cursor resource.
     */
    private suspend inline fun handleCursorForDeque(
        cursor: Cursor,
        collector: FlowCollector<Deque<Question>>
    ) {
        cursor.use {
            if (cursor.count == 0) {
                collector.emit(LinkedList())
            } else {
                val list = LinkedList<Question>()
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