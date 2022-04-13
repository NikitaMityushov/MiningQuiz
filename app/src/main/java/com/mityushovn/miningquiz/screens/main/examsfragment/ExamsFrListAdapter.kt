package com.mityushovn.miningquiz.screens.main.examsfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.databinding.ExamItemBinding
import com.mityushovn.miningquiz.models.Exam

class ExamsFrListAdapter : RecyclerView.Adapter<ExamsFrListAdapter.ExamViewHolder>() {

    var list: List<Exam> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.exam_item, parent, false)
        return ExamViewHolder(ExamItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ExamViewHolder(val binding: ExamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exam: Exam) {
            with(binding) {
                examItemTv.text = exam.nameExam
            }
        }
    }
}