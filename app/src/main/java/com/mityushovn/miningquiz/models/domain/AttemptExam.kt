package com.mityushovn.miningquiz.models.domain

import com.mityushovn.miningquiz.utils.now
import java.util.Date
import kotlin.random.Random

/**
 * Represents attempt of passing an exam.
 */
data class AttemptExam(
    val attExamId: Int = Random(System.currentTimeMillis()).nextInt(),
    val examId: Int,
    val passedAt: Date = Date().now(),
    val success: Boolean // false == 0, true == 1 in the DB
)