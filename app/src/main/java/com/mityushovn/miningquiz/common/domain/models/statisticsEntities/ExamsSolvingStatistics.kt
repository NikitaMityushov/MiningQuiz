package com.mityushovn.miningquiz.common.domain.models.statisticsEntities

/**
 * AbstractStatistics implementation for Exam entities
 */
class ExamsSolvingStatistics(
    numberOfAttempts: Int,
    numberOfSuccessAttempts: Int,
    numberOfFailedAttempts: Int,
) : AbstractStatistics(numberOfAttempts, numberOfSuccessAttempts, numberOfFailedAttempts)