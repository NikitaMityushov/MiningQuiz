package com.mityushovn.miningquiz.kmm_domain.domain.models.statisticsEntities

/**
 * AbstractStatistics implementation for Topic entities
 */
class TopicsSolvingStatistics(
    numberOfAttempts: Int,
    numberOfSuccessAttempts: Int,
    numberOfFailedAttempts: Int,
) : AbstractStatistics(numberOfAttempts, numberOfSuccessAttempts, numberOfFailedAttempts)