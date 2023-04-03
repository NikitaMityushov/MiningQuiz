package com.mityushovn.miningquiz.data_attempts.internal.data

internal object AppSQLiteContract {
    const val DATABASE_NAME = "database_quiz"

    object Common {
        const val NUMBER_OF_ATTEMPTS = "number_of_attempts"
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
