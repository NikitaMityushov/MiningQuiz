package com.mityushovn.quizlist_feature.internal.presentation.topicslistfragment

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.core_domain.domain.models.Topic
import com.mityushovn.quizlist_feature.internal.presentation.topicslistfragment.recyclerview.TopicsListFrAdapter


/**
 * Binding adapters for TopicsListFragment
 */

/**
 * Binds RecyclerView.Adapter with source list.
 */
@BindingAdapter("topics", "listAdapter")
internal fun bindRecyclerView(rv: RecyclerView, list: List<Topic>, listAdapter: TopicsListFrAdapter) {
    listAdapter.submitList(list)
}