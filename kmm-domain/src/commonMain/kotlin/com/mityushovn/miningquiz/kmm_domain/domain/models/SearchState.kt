package com.mityushovn.miningquiz.kmm_domain.domain.models

sealed class SearchState
object Loading : SearchState()
object Ready : SearchState()
class Error(val e: Throwable) : SearchState()