package com.mityushovn.miningquiz.debug.screens

import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.core_testing.ui.pages.RecyclerViewObject

private const val topicsRecyclerId = R.id.topics_list_fr_recycler_view

class TopicsFragmentScreen {


    companion object {
        private var instance: TopicsFragmentScreen? = null

        private fun getTopicsFragmentScreen(): TopicsFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = TopicsFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun topicsFragmentScreen(func: TopicsFragmentScreen.() -> Unit) =
            getTopicsFragmentScreen().apply(func)
    }

    fun clickTopicsRecycleViewOnPosition(position: Int) {
        com.mityushovn.miningquiz.core_testing.ui.pages.RecyclerViewObject.recycleViewWithId(topicsRecyclerId) {
            scrollToPosition(position)
            clickAtPosition(position)
        }
    }
}