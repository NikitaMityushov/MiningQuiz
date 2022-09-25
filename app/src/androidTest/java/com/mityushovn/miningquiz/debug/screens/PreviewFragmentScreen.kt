package com.mityushovn.miningquiz.debug.screens

import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.debug.pages.ViewObject.Companion.viewWithId

private const val LETS_START_BTN_ID = R.id.preview_fr_start_btn

class PreviewFragmentScreen {

    companion object {
        private var instance: PreviewFragmentScreen? = null

        private fun getPreviewFragmentScreen(): PreviewFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = PreviewFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun quizPreviewFragmentScreen(func: PreviewFragmentScreen.() -> Unit) =
            getPreviewFragmentScreen().apply(func)
    }

    fun clickLetsStartBtn() {
        viewWithId(LETS_START_BTN_ID) {
            scrollTo()
            click()
        }
    }
}