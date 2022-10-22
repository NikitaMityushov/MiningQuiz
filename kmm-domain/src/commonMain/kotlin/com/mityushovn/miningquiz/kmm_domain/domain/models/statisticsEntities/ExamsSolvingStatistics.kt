package com.mityushovn.miningquiz.kmm_domain.domain.models.statisticsEntities

/**
 * AbstractStatistics implementation for Exam entities
 */
class ExamsSolvingStatistics(
    numberOfAttempts: Int,
    numberOfSuccessAttempts: Int,
    numberOfFailedAttempts: Int,
) : AbstractStatistics(numberOfAttempts, numberOfSuccessAttempts, numberOfFailedAttempts)