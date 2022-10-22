package com.mityushovn.miningquiz.statistics_feature.api

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies

interface StatisticsDeps: Dependencies {
    val db: SQLiteDatabase
}