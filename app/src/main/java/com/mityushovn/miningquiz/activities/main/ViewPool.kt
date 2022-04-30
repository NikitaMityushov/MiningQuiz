package com.mityushovn.miningquiz.activities.main

import androidx.recyclerview.widget.RecyclerView

interface ViewPool {
    fun getViewPool(): RecyclerView.RecycledViewPool
}