package com.mityushovn.miningquiz.common.di.modules

import android.database.sqlite.SQLiteDatabase
import com.mityushovn.miningquiz.data_questions.api.QuestionsRepositoryFactory
import com.mityushovn.miningquiz.main.presentation.activity.MainActivityVMFactory
import dagger.Module
import dagger.Provides

@Module
object ViewModelModule {

    @Provides
    fun provideMainActivityVMFactory(db: SQLiteDatabase): MainActivityVMFactory {
        return MainActivityVMFactory(
            questionsRepository = QuestionsRepositoryFactory.createInstance(db)
        )
    }
}