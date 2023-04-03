package com.mityushovn.miningquiz.debug.screens

import com.mityushovn.miningquiz.core_testing.ui.pages.RecyclerViewObject.Companion.recycleViewWithId
import com.mityushovn.miningquiz.R

private const val RECYCLER_VIEW_ID = R.id.search_list_fr_recycler_view

class SearchListFragmentScreen {

    companion object {
        private var instance: SearchListFragmentScreen? = null

        private fun getSearchListFragmentScreen(): SearchListFragmentScreen {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SearchListFragmentScreen()
                    }
                }
            }
            return instance!!
        }

        fun searchListFragmentScreen(func: SearchListFragmentScreen.() -> Unit) =
            getSearchListFragmentScreen().apply(func)
    }

    fun clickRecyclerViewItem(position: Int) {
        recycleViewWithId(RECYCLER_VIEW_ID) {
            clickAtPosition(position)
        }
    }
}