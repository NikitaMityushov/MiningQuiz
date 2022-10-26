package com.mityushovn.miningquiz.database

/**
 * Kotlin object with all names of tables and columns from the application database.
 * todo(11.04.22 Вставить картинку схемы базы данных)
 */
object AppSQLiteContract {
    const val DATABASE_NAME = "database_quiz"

    object Common {
        const val SUCCESS = "success"
        const val NUMBER_OF_ATTEMPTS = "number_of_attempts"
        const val COLUMN_PASSED_AT = "passed_at"
    }

    object QuestionTable {
        const val TABLE_NAME = "question"
        const val QUESTIONS_VIEW_NAME = "question_view"
        const val COLUMN_QUESTION_ID = "question_id"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_RIGHT_ANS = "right_ans"
        const val COLUMN_ANS_1 = "ans_1"
        const val COLUMN_ANS_2 = "ans_2"
        const val COLUMN_ANS_3 = "ans_3"
        const val SELECT_ALL_WRONG_ANSWERED_QUERY =
            "SELECT	* FROM	question_view INNER JOIN wrong_answered USING(question_id)"
    }

    object TopicTable {
        const val TABLE_NAME = "topic"
        const val COLUMN_TOPIC_ID = "topic_id"
        const val COLUMN_NAME_TOPIC = "name_topic"
        const val SELECT_ALL_TOPICS_QUERY =
            "SELECT topic_id, name_topic, name_exam FROM exam INNER JOIN topic USING(exam_id)"
    }

    object ExamTable {
        const val TABLE_NAME = "exam"
        const val COLUMN_EXAM_ID = "exam_id"
        const val COLUMN_NAME_EXAM = "name_exam"
    }

    object WrongAnsweredTable {
        const val TABLE_NAME = "wrong_answered"
        const val COLUMN_WRONG_ID = "wrong_id"
        const val COLUMN_ANSWERED_AT = "answered_at"
    }

    object AttemptTopicTable {
        const val TABLE_NAME = "attempt_topic"
        const val COLUMN_ATT_TOPIC_ID = "att_topic_id"

        const val GET_NUMBER_OF_ALL_TOPICS_SOLVING_ATTEMPTS_QUERY =
            "SELECT count(att_topic_id) AS number_of_attempts FROM attempt_topic"
        const val GET_NUMBER_OF_SUCCESS_TOPIC_SOLVING_ATTEMPTS_QUERY =
            "SELECT count(att_topic_id) AS number_of_attempts FROM attempt_topic WHERE success = 1"
        const val GET_NUMBER_OF_FAILED_TOPIC_SOLVING_ATTEMPTS_QUERY =
            "SELECT count(att_topic_id) AS number_of_attempts FROM attempt_topic WHERE success = 0"
        const val DELETE_ALL_TOPIC_ATTEMPTS = "DELETE FROM attempt_topic"
    }

    object AttemptExamTable {
        const val TABLE_NAME = "attempt_exam"
        const val COLUMN_ATT_EXAM_ID = "att_exam_id"


        const val GET_NUMBER_OF_ALL_EXAM_SOLVING_ATTEMPTS_QUERY =
            "SELECT count(att_exam_id) AS number_of_attempts FROM attempt_exam"
        const val GET_NUMBER_OF_SUCCESS_EXAM_SOLVING_ATTEMPTS_QUERY =
            "SELECT count(att_exam_id) AS number_of_attempts FROM attempt_exam WHERE success = 1"
        const val GET_NUMBER_OF_FAILED_EXAM_SOLVING_ATTEMPTS_QUERY =
            "SELECT count(att_exam_id) AS number_of_attempts FROM attempt_exam WHERE success = 0"
        const val DELETE_ALL_EXAM_ATTEMPTS = "DELETE FROM attempt_exam"
    }
}
