package com.mityushovn.miningquiz.di.modules

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.database.AppSQLiteHelper
import com.mityushovn.miningquiz.database.attemptExamDao.AttemptExamDao
import com.mityushovn.miningquiz.database.examDao.ExamDaoAPI
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {
    @Provides
    fun provideDatabase(applicationContext: Context): SQLiteDatabase {
        return AppSQLiteHelper(applicationContext).writableDatabase
    }
}