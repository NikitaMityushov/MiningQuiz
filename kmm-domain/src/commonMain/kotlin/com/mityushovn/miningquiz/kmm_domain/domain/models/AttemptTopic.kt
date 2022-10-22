package com.mityushovn.miningquiz.kmm_domain.domain.models

import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

/**
 * Represents attempt of passing a topic.
 */
data class AttemptTopic(
    val attTopicId: Int = Random(now().epochSeconds).nextInt(),
    val topicId: Int,
    val passedAt: LocalDateTime = now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val success: Boolean // false == 0, true == 1 in the DB
)