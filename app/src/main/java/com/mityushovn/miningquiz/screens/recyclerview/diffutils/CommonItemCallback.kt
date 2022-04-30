package com.mityushovn.miningquiz.screens.recyclerview.diffutils

import androidx.recyclerview.widget.DiffUtil.ItemCallback

/**
 * Common implementation for all DiffUtil.ItemCallback classes in the application.
 * @property areItemsTheSame and
 * @property areContentsTheSame are lambdas that corresponds comparison logic of source items.
 */
class CommonItemCallback<T>(
    private inline val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private inline val areContentsTheSame: (oldItem: T, newItem: T) -> Boolean
) : ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentsTheSame(oldItem, newItem)
    }
}