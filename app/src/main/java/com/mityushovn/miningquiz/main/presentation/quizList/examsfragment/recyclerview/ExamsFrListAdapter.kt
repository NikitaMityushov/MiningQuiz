package com.mityushovn.miningquiz.presentation.screens.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.databinding.ExamItemBinding
import com.mityushovn.miningquiz.common.domain.models.Exam

// TODO: 10.05.2022 Animations, ItemDecorations

/**
 * RecyclerView.Adapter class for List<Exam>.
 * Extends ListAdapter because a diff between lists will be computed on a background thread.
 * @property clickListener is a handler for list item click navigation.
 */
class ExamsFrListAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<Exam, ExamViewHolder>(ExamsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.exam_item, parent, false)
        return ExamViewHolder(ExamItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    /**
     * private class implements DiffUtil.ItemCallback for List<Exam>
     */
    private class ExamsDiffCallback : DiffUtil.ItemCallback<Exam>() {
        override fun areItemsTheSame(oldItem: Exam, newItem: Exam): Boolean {
            return oldItem.examId == newItem.examId
        }

        override fun areContentsTheSame(oldItem: Exam, newItem: Exam): Boolean {
            return oldItem == newItem
        }

    }
}

/**
 * View Holder class for exam_item.xml that binds data with inflated View.
 * Contains static DiffUtil.ItemCallback class for RecyclerView.Adapter.
 */
class ExamViewHolder(val binding: ExamItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(exam: Exam, clickListener: (Int) -> Unit) {
        with(binding) {
            examCountTv.text = (this@ExamViewHolder.adapterPosition.inc()).toString()
            examItemTv.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    exam.nameExam,
                    examItemTv.textMetricsParamsCompat,
                    null
                )
            )
            executePendingBindings() // !! essential for bindings, forcing the framework to update view right at the moment
        }

        onClickListener(exam.examId, clickListener)
    }

    private fun onClickListener(examId: Int, onClick: (Int) -> Unit) {
        itemView.setOnClickListener {
            onClick(examId)
        }
    }
}
