package com.mityushovn.miningquiz.quizlist_feature.internal.presentation.examsfragment

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.core_domain.domain.models.Exam
import com.mityushovn.miningquiz.quizlist_feature.internal.presentation.examsfragment.recyclerview.ExamsFrListAdapter

/**
 * Binding adapters for ExamsFragment.
 */

/**
 * Binds RecyclerView.Adapter with source list.
 */
@BindingAdapter("exams", "listAdapter")
internal fun bindRecyclerView(rv: RecyclerView, list: List<Exam>, listAdapter: ExamsFrListAdapter) {
    listAdapter.submitList(list)
}