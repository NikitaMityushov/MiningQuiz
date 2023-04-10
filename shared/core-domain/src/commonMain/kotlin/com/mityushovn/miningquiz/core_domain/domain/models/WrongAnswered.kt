package com.mityushovn.miningquiz.core_domain.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

/**
 * Represents wrong answered question.
 */
data class WrongAnswered(
    val wrongId: Int = Random(System.currentTimeMillis()).nextInt(),
    val questionId: Int,
    val answeredAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
)