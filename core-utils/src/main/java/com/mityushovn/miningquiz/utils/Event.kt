package com.mityushovn.miningquiz.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Class that represents a generic event.
 * @param <T> the type of the content which is delivered to the observer.
 * When get() is called, to [_value] assigns null. Event may be handled only once.
 */
class Event<T : Any>(
    value: T? = null
) {
    private var _value: T? = value

    fun get(): T? {
        val value = _value
        this._value = null
        return value
    }
}

typealias MutableLiveEvent<T> = MutableStateFlow<Event<T>>
typealias LiveEvent<T> = StateFlow<Event<T>>
typealias EventCollector<T> = (T) -> Unit

fun <T : Any> MutableLiveEvent<T>.share(): LiveEvent<T> {
    return this
}

fun <T : Any> MutableLiveEvent<T>.postEvent(value: T) {
    this.value = Event(value)
}

fun <T : Any> LiveEvent<T>.collectEventOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    collector: EventCollector<T>
) {
    with(lifecycleOwner) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect { event ->
                    event.get()?.let {
                        collector(it)
                    }
                }
            }
        }
    }
}