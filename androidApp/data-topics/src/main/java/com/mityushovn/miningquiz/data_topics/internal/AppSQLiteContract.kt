package com.mityushovn.miningquiz.data_topics.internal

/**
 * Kotlin object with all names of tables and columns from the application database.
 * todo(11.04.22 Вставить картинку схемы базы данных)
 */
internal object AppSQLiteContract {

    object TopicTable {
        const val COLUMN_TOPIC_ID = "topic_id"
        const val COLUMN_NAME_TOPIC = "name_topic"
        const val SELECT_ALL_TOPICS_QUERY =
            "SELECT topic_id, name_topic, name_exam FROM exam INNER JOIN topic USING(exam_id)"
    }

    object ExamTable {
        const val COLUMN_NAME_EXAM = "name_exam"
    }
}
