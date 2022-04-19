package com.mityushovn.miningquiz.screens.main.quizList.examsfragment

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.models.Exam
import com.mityushovn.miningquiz.screens.recyclerview.adapters.ExamsFrListAdapter

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