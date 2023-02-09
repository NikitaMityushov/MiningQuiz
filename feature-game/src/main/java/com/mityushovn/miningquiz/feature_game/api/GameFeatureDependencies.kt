package com.mityushovn.miningquiz.feature_game.api

import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies

interface GameFeatureDependencies : Dependencies {
    val db: SQLiteDatabase
    val prefs: SharedPreferences
}
