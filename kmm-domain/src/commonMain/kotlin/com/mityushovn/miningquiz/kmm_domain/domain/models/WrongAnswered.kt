package com.mityushovn.miningquiz.kmm_domain.domain.models

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

/**
 * Represents wrong answered question.
 */
data class WrongAnswered(
    val wrongId: Int = Random(now().epochSeconds).nextInt(),
    val questionId: Int,
    val answeredAt: LocalDateTime = now().toLocalDateTime(TimeZone.currentSystemDefault())
)