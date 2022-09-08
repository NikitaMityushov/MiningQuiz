package com.mityushovn.miningquiz.main.presentation.activity

sealed class SearchState
object Loading : SearchState()
object Ready : SearchState()
class Error(val e: Throwable) : SearchState()