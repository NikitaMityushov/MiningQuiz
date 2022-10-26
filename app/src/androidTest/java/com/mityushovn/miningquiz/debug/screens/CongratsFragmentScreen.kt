package com.mityushovn.miningquiz.debug.screens

import com.mityushovn.miningquiz.core_testing.ui.pages.ViewObject.Companion.viewWithId
import com.mityushovn.miningquiz.R

private const val CONTINUE_BUTTON_ID = R.id.congrats_fr_continue_btn
private const val REPEAT_BUTTON_ID = R.id.congrats_fr_repeat_btn

class CongratsFragmentScreen {

    companion object {
        private var instance: CongratsFragmentScreen? = null

        private fun getCongratsFragmentScreen(): CongratsFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = CongratsFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun congratsFragmentScreen(func: CongratsFragmentScreen.() -> Unit) =
            getCongratsFragmentScreen().apply(func)
    }

    fun clickContinueBtn() {
        viewWithId(CONTINUE_BUTTON_ID) {
            scrollTo()
            click()
        }
    }

    fun clickRepeatBtn() {
        viewWithId(REPEAT_BUTTON_ID) {
            scrollTo()
            click()
        }
    }
}