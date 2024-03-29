package com.mityushovn.miningquiz.quizlist_feature.internal.presentation.topicslistfragment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushovn.miningquiz.core_domain.domain.models.Topic
import com.mityushovn.miningquiz.quizlist_feature.R
import com.mityushovn.miningquiz.quizlist_feature.databinding.TopicItemBinding

// TODO: 10.05.2022 Animations, ItemDecorations

/**
 * RecyclerView.Adapter class for List<Topic>.
 * Extends ListAdapter because a diff between lists will be computed on a background thread.
 * @property clickListener is a handler for list item click navigation.
 */
internal class TopicsListFrAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<Topic, TopicViewHolder>(TopicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
        return TopicViewHolder(TopicItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    /**
     * private class implements DiffUtil.ItemCallback for List<Topic>
     */
    private class TopicDiffCallback : DiffUtil.ItemCallback<Topic>() {
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.topicId == newItem.topicId
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem == newItem
        }

    }
}

/**
 * View Holder class for topic_item.xml that binds data with inflated View.
 * Contains static DiffUtil.ItemCallback class for RecyclerView.Adapter.
 */
class TopicViewHolder(val binding: TopicItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(topic: Topic, clickListener: (Int) -> Unit) {
        with(binding) {
            topicCountTv.text =
                (this@TopicViewHolder.adapterPosition.inc()).toString() // TODO: 21.04.2022 подумать, возможно убрать инкремент
//            topicItemTv.text = topic.nameTopic
            topicItemTv.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    topic.nameTopic,
                    topicItemTv.textMetricsParamsCompat,
                    null
                )
            )
            executePendingBindings() // !! essential for bindings, forcing the framework to update view right at the moment
        }
        onClickListener(topic.topicId, clickListener)
    }

    private fun onClickListener(topicId: Int, onClick: (Int) -> Unit) {
        itemView.setOnClickListener {
            onClick(topicId)
        }
    }
}
