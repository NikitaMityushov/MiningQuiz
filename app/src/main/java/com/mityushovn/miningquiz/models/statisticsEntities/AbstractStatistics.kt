package com.mityushovn.miningquiz.models.statisticsEntities

/**
 * Common abstract class for Exams, Topics and Questions statistics
 */
abstract class AbstractStatistics(
    val numberOfAttempts: Int = 0,
    val numberOfSuccessAttempts: Int = 0,
    val numberOfFailedAttempts: Int = 0
)