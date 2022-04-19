package com.mityushovn.miningquiz.screens.main.searchlistfragment

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.models.Question
import com.mityushovn.miningquiz.screens.recyclerview.adapters.QuestionsSearchFrAdapter
import timber.log.Timber

/**
 * Binding adapters for SearchListFragment
 */


/**
 * Observe Questions LiveData and after it emitted change list in the RecyclerView adapter.
 */
@BindingAdapter("questions", "listAdapter")
fun bindRecyclerView(
    rv: RecyclerView,
    list: List<Question>,
    listAdapter: QuestionsSearchFrAdapter
) {
    Timber.d("bindRecycler list size is ${list.size}")
    listAdapter.submitList(list)
}

/**
 * Observe Questions LiveData and after it emitted check if list is empty and update TextView.
 */
@BindingAdapter("sizeOfListForTextView")
fun bindNumberOfItemsListTextView(tv: TextView, list: List<Question>) {
    Timber.d("list size for text view is ${list.size}")
    if (list.isEmpty()) {
        tv.setText(R.string.there_are_no_questions)
    } else {
        tv.setText(R.string.questions_found)
        tv.append(list.size.toString())
    }
}