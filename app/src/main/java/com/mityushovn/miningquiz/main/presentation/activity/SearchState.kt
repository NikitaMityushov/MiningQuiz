package com.mityushovn.miningquiz.presentation.activities.main

sealed class SearchState
object Loading : SearchState()
object Ready : SearchState()