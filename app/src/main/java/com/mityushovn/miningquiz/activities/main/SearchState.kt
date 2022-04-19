package com.mityushovn.miningquiz.activities.main

sealed class SearchState
object Loading : SearchState()
object Ready : SearchState()