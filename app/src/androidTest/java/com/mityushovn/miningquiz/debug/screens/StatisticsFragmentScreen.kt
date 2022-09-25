package com.mityushovn.miningquiz.debug.screens

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.mityushovn.miningquiz.debug.pages.ViewObject.Companion.viewWithId
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.debug.pages.TextViewObject.Companion.textViewWithId
import com.mityushovn.miningquiz.debug.pages.ToastObject.Companion.toastWithText

private const val RESET_BUTTON_ID = R.id.stat_fr_reset_statistics_btn
private const val EXAMS_ATT_TV_ID = R.id.stat_fr_attempts_exam_tv
private const val TOPICS_ATT_TV_ID = R.id.stat_fr_attempts_topic_tv

class StatisticsFragmentScreen {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    companion object {
        private var instance: StatisticsFragmentScreen? = null

        private fun getStatisticsFragmentScreen(): StatisticsFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = StatisticsFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun statisticsFragmentScreen(func: StatisticsFragmentScreen.() -> Unit) =
            getStatisticsFragmentScreen().apply(func)
    }

    fun clickResetStatisticsBtn() {
        viewWithId(RESET_BUTTON_ID) {
            scrollTo()
            click()
        }
    }

    fun compareExamsAttemptsText(text: String) {
        textViewWithId(EXAMS_ATT_TV_ID) {
            isTextSame(text)
        }
    }

    fun compareTopicsAttemptsText(text: String) {
        textViewWithId(TOPICS_ATT_TV_ID) {
            isTextSame(text)
        }
    }

    fun clickResetDialogYes() {
        viewWithId(android.R.id.button1) {
            click()
        }
    }

    fun clickResetDialogNo() {
        viewWithId(android.R.id.button2) {
            click()
        }
    }

    fun checkToastIsDisplayed() {
        val text = context.getString(R.string.stat_deleted_successfuly)
        toastWithText(text) {
            checkIsDisplayed()
        }
    }

}