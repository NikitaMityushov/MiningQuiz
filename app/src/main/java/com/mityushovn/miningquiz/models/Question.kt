package com.mityushovn.miningquiz.models

/**
 * Represents a question entity
 */
data class Question(
    val questionId: Int,
    val topicId: Int,
    val nameTopic: String,
    val examId: Int,
    val nameExam: String,
    val content: String,
    val rightAns: String,
    val ans1: String,
    val ans2: String,
    val ans3: String
)