package com.mityushovn.miningquiz.debug.screens

import com.mityushovn.miningquiz.core_testing.ui.pages.ViewObject.Companion.viewWithId
import com.mityushovn.miningquiz.R

private const val CONTINUE_BUTTON_ID = R.id.failed_fr_continue_btn
private const val REPEAT_BUTTON_ID = R.id.failed_fr_repeat_btn

class FailedFragmentScreen {

    companion object {
        private var instance: FailedFragmentScreen? = null

        private fun getFailedFragmentScreen(): FailedFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = FailedFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun failedFragmentScreen(func: FailedFragmentScreen.() -> Unit) =
            getFailedFragmentScreen().apply(func)
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