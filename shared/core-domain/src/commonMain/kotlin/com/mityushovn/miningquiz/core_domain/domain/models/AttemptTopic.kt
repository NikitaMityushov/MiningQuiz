package com.mityushovn.miningquiz.core_domain.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

/**
 * Represents attempt of passing a topic.
 */
data class AttemptTopic(
    val attTopicId: Int = Random(System.currentTimeMillis()).nextInt(),
    val topicId: Int,
    val passedAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val success: Boolean // false == 0, true == 1 in the DB
)