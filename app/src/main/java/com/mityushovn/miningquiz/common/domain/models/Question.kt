package com.mityushovn.miningquiz.common.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a question entity
 */
@Parcelize
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
) : Parcelable