package com.mityushovn.miningquiz.data_questions.internal

internal object AppSQLiteContract {
    object QuestionTable {
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
        const val COLUMN_TOPIC_ID = "topic_id"
        const val COLUMN_NAME_TOPIC = "name_topic"
    }

    object ExamTable {
        const val COLUMN_EXAM_ID = "exam_id"
        const val COLUMN_NAME_EXAM = "name_exam"
    }
}