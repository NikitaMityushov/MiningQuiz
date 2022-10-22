package com.mityushovn.miningquiz.game_feature.api

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.module_injector.interfaces.Dependencies

interface GameFeatureDependencies : Dependencies {
    val db: SQLiteDatabase
}
