package com.mityushovn.miningquiz.models.statisticsEntities

/**
 * AbstractStatistics implementation for Exam entities
 */
class ExamsSolvingStatistics(
    numberOfAttempts: Int,
    numberOfSuccessAttempts: Int,
    numberOfFailedAttempts: Int,
) : AbstractStatistics(numberOfAttempts, numberOfSuccessAttempts, numberOfFailedAttempts)