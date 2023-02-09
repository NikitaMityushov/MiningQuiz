package com.mityushovn.miningquiz.feature_quizlist.api

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies

interface QuizlistFeatureDependencies : Dependencies {
    val db: SQLiteDatabase
}