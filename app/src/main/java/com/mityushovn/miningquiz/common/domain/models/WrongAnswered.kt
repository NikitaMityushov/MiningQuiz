package com.mityushovn.miningquiz.common.domain.models

import com.mityushovn.miningquiz.utils.now
import java.util.Date
import kotlin.random.Random

/**
 * Represents wrong answered question.
 */
data class WrongAnswered(
    val wrongId: Int = Random(System.currentTimeMillis()).nextInt(),
    val questionId: Int,
    val answeredAt: Date = Date().now()
)