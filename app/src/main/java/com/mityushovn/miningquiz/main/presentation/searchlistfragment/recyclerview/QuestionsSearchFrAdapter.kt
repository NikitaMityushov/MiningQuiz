package com.mityushovn.miningquiz.main.presentation.searchlistfragment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.core_domain.domain.models.Question
import com.mityushovn.miningquiz.databinding.QuestionItemBinding

// TODO: 10.05.2022 Animations, ItemDecorations

/**
 * RecyclerView.Adapter class for List<Question>.
 * Extends ListAdapter because a diff between lists will be computed on a background thread.
 * @property clickListener is a handler for list item click navigation.
 */
class QuestionsSearchFrAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<Question, QuestionViewHolder>(QuestionDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return QuestionViewHolder(QuestionItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    /**
     * private class implements DiffUtil.ItemCallback for List<Question>
     */
    private class QuestionDiffCallback : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.questionId == newItem.questionId
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }

}

/**
 * View Holder class for question_item.xml that binds data with inflated View.
 * Contains static DiffUtil.ItemCallback class for RecyclerView.Adapter.
 */
class QuestionViewHolder(
    val binding: QuestionItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(question: Question, clickListener: (Int) -> Unit) {

        with(binding) {
            questionCountTv.text = (this@QuestionViewHolder.adapterPosition.inc()).toString()
            // prefetch strings with PrecomputedText
            questionItemTv.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    question.content,
                    questionItemTv.textMetricsParamsCompat,
                    null
                )
            )
            executePendingBindings() // !! essential for bindings, forcing the framework to update view right at the moment
        }

        onClickListener(question.questionId, clickListener)
    }

    private fun onClickListener(questionId: Int, onClick: (Int) -> Unit) {
        itemView.setOnClickListener {
            onClick(questionId)
        }
    }
}
