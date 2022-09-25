package com.mityushovn.miningquiz.debug.screens

import com.mityushovn.miningquiz.debug.pages.ViewObject.Companion.viewWithId
import com.mityushovn.miningquiz.R

private const val EXAM_TV_ID = R.id.question_fr_exam_tv

class QuestionFragmentScreen {

    companion object {
        private var instance: QuestionFragmentScreen? = null

        private fun getQuestionFragmentScreen(): QuestionFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = QuestionFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun questionFragmentScreen(func: QuestionFragmentScreen.() -> Unit) =
            getQuestionFragmentScreen().apply(func)
    }

    fun checkIsOnScreen() {
        viewWithId(EXAM_TV_ID) {
            checkIsDisplayed()
        }
    }
}