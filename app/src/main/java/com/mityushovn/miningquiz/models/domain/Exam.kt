package com.mityushovn.miningquiz.models.domain

/**
 * Representation of exam entity.
 * Every exam has unique identifier and name.
 */
data class Exam(
    val examId: Int,
    val nameExam: String
)