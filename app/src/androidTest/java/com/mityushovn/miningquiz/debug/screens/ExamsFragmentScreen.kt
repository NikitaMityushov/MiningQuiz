package com.mityushovn.miningquiz.debug.screens

import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.debug.pages.RecyclerViewObject

private const val examsRecyclerId = R.id.exam_fr_recycler_view

class ExamsFragmentScreen {

    companion object {
        private var instance: ExamsFragmentScreen? = null

        private fun getExamsFragmentScreen(): ExamsFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = ExamsFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun examsFragmentScreen(func: ExamsFragmentScreen.() -> Unit) =
            getExamsFragmentScreen().apply(func)
    }

    fun clickExamsRecycleViewOnPosition(position: Int) {
        RecyclerViewObject.recycleViewWithId(examsRecyclerId) {
            scrollToPosition(position)
            clickAtPosition(position)
        }
    }

}