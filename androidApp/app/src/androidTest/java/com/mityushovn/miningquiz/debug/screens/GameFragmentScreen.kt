package com.mityushovn.miningquiz.debug.screens

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.core_testing.ui.pages.ViewObject.Companion.viewWithId

private const val FIRST_ANS_ID = R.id.game_fr_answer_1
private const val SECOND_ANS_ID = R.id.game_fr_answer_2
private const val THIRD_ANS_ID = R.id.game_fr_answer_3
private const val FOURTH_ANS_ID = R.id.game_fr_answer_4
private const val POSTPONE_BTN_ID = R.id.game_fr_postpone_btn
private const val EXIT_BTN_ID = R.id.game_fr_exit_game_btn

class GameFragmentScreen {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    companion object {
        private var instance: GameFragmentScreen? = null

        private fun getGameFragmentScreen(): GameFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = GameFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun gameFragmentScreen(func: GameFragmentScreen.() -> Unit) =
            getGameFragmentScreen().apply(func)
    }

    fun clickFirstAnswer() {
        viewWithId(FIRST_ANS_ID) {
            scrollTo()
            click()
        }
    }

    fun clickSecondAnswer() {
        viewWithId(SECOND_ANS_ID) {
            scrollTo()
            click()
        }
    }

    fun clickThirdAnswer() {
        viewWithId(THIRD_ANS_ID) {
            scrollTo()
            click()
        }
    }

    fun clickFourthAnswer() {
        viewWithId(FOURTH_ANS_ID) {
            scrollTo()
            click()
        }
    }

    fun clickPostponeBtn() {
        viewWithId(POSTPONE_BTN_ID) {
            click()
        }
    }

    fun clickExitGameBtn() {
        viewWithId(EXIT_BTN_ID) {
            click()
        }
    }

    fun clickContinueBtn() {
        viewWithId(R.id.game_fr_continue_btn) {
            click()
        }
    }

    fun clickExitDialogYes() {
        viewWithId(android.R.id.button1) {
            click()
        }
    }

    fun clickExitDialogNo() {
        viewWithId(android.R.id.button2) {
            click()
        }
    }

    fun checkToastIsDisplayed() {
        val text = context.getString(R.string.attempt_succ_added)
        com.mityushovn.miningquiz.core_testing.ui.pages.ToastObject.toastWithText(text) {
            checkIsDisplayed()
        }
    }
}