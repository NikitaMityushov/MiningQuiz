package com.mityushovn.miningquiz.activities.main

import androidx.recyclerview.widget.RecyclerView

// TODO: 13.06.2022 common viewpool for recyclerview
interface ViewPool {
    fun getViewPool(): RecyclerView.RecycledViewPool
}