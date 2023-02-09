package com.mityushovn.miningquiz.feature_statistics.api

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies

interface StatisticsDeps: Dependencies {
    val db: SQLiteDatabase
}