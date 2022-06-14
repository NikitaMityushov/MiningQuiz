package com.mityushovn.miningquiz.screens.main.quizList.topicslistfragment

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.models.domain.Topic
import com.mityushovn.miningquiz.screens.recyclerview.adapters.TopicsListFrAdapter


/**
 * Binding adapters for TopicsListFragment
 */

/**
 * Binds RecyclerView.Adapter with source list.
 */
@BindingAdapter("topics", "listAdapter")
fun bindRecyclerView(rv: RecyclerView, list: List<Topic>, listAdapter: TopicsListFrAdapter) {
    listAdapter.submitList(list)
}