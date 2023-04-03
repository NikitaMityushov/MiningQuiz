package com.mityushovn.miningquiz.debug.screens

import androidx.test.platform.app.InstrumentationRegistry
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.core_testing.ui.pages.TextViewObject.Companion.textViewWithText

private const val examsTabStr = R.string.quiz
private const val topicsTabStr = R.string.topics

class QuizListFragmentScreen {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    companion object {
        private var instance: QuizListFragmentScreen? = null

        private fun getMainActivityScreen(): QuizListFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = QuizListFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun quizListFragmentScreen(func: QuizListFragmentScreen.() -> Unit) =
            getMainActivityScreen().apply(func)
    }

    fun clickExamsTab() {
        textViewWithText(context.getString(examsTabStr)) {
            scrollTo()
            click()
        }
    }

    fun clickTopicsTab() {
        textViewWithText(context.getString(topicsTabStr)) {
            scrollTo()
            click()
        }
    }

}