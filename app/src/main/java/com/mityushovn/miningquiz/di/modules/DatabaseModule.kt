package com.mityushovn.miningquiz.di.modules

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.database.AppSQLiteHelper
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {
    @Provides
    fun provideDatabase(application: Application): SQLiteDatabase {
        return AppSQLiteHelper(application.applicationContext).writableDatabase
    }
}