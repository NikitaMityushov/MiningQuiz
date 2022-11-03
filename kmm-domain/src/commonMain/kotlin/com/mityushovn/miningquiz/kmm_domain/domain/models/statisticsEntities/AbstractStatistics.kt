package com.mityushovn.miningquiz.kmm_domain.domain.models.statisticsEntities

/**
 * Common abstract class for Exams, Topics and Questions statistics
 */
abstract class AbstractStatistics(
    var numberOfAttempts: Int = 0,
    var numberOfSuccessAttempts: Int = 0,
    var numberOfFailedAttempts: Int = 0
)