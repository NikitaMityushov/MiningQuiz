package com.mityushovn.miningquiz.main.presentation.quizList.examsfragment

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.common.domain.models.Exam
import com.mityushovn.miningquiz.main.presentation.quizList.examsfragment.recyclerview.ExamsFrListAdapter

/**
 * Binding adapters for ExamsFragment.
 */

/**
 * Binds RecyclerView.Adapter with source list.
 */
@BindingAdapter("exams", "listAdapter")
fun bindRecyclerView(rv: RecyclerView, list: List<Exam>, listAdapter: ExamsFrListAdapter) {
    listAdapter.submitList(list)
}