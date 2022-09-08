package com.mityushovn.miningquiz.debug

import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Coroutine which execution can be controlled outside. May be
 * useful for testing purposes.
 */
class CoroutineSubject<T> {

    private var continuation: Continuation<T>? = null

    suspend fun get(): T = suspendCoroutine {
        this.continuation = it
    }

    fun sendError(e: Exception) {
        continuation?.resumeWithException(e)
        continuation = null
    }

    fun sendSuccess(value: T) {
        continuation?.resume(value)
    }
}
