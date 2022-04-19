package com.mityushovn.miningquiz.screens.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.databinding.ExamItemBinding
import com.mityushovn.miningquiz.models.Exam

// TODO: 10.05.2022 Animations, ItemDecorations

/**
 * RecyclerView.Adapter class for List<Exam>.
 * Extends ListAdapter because a diff between lists will be computed on a background thread.
 * @property clickListener is a handler for list item click navigation.
 */
class ExamsFrListAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<Exam, ExamViewHolder>(ExamViewHolder.getDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.exam_item, parent, false)
        return ExamViewHolder(ExamItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
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
            examItemTv.text = exam.nameExam
            executePendingBindings() // !! essential for bindings, forcing the framework to update view right at the moment
        }

        onClickListener(exam.examId, clickListener)
    }

    private fun onClickListener(examId: Int, onClick: (Int) -> Unit) {
        itemView.setOnClickListener {
            onClick(examId)
        }
    }

    /*
        static fields and methods
     */
    companion object {
        fun getDiffUtil() = diffUtil

        /**
         * static instance of DiffUtil.ItemCallback<Exam> class
         */
        private val diffUtil = object : DiffUtil.ItemCallback<Exam>() {
            override fun areItemsTheSame(oldItem: Exam, newItem: Exam): Boolean {
                return oldItem.examId == newItem.examId
            }

            override fun areContentsTheSame(oldItem: Exam, newItem: Exam): Boolean {
                return oldItem == newItem
            }

        }
    }
}
