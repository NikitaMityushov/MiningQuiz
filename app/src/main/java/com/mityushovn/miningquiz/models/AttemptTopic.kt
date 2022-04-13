package com.mityushovn.miningquiz.models

import com.mityushovn.miningquiz.utils.now
import java.util.Date
import kotlin.random.Random

/**
 * Represents attempt of passing a topic.
 */
data class AttemptTopic(
    val attTopicId: Int = Random(System.currentTimeMillis()).nextInt(),
    val topicId: Int,
    val passedAt: Date = Date().now(),
    val success: Boolean // false == 0, true == 1 in the DB
)