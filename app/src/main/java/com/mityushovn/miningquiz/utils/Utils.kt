package com.mityushovn.miningquiz.utils

import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * @author
 * @since 1.0
 * Utility functions for application
 */

/**
 * Converts UNIX time in the java.util.Date instance
 */
fun Long.toDate(): Date {
    return Date(TimeUnit.MILLISECONDS.convert(this, TimeUnit.SECONDS))
}

/**
 * Returns current time from the Date instance
 */
fun Date.now(): Date {
    return Date(System.currentTimeMillis())
}

/**
 * SQLite doesn't have boolean data types.
 * false == 0, true == 1
 */
fun Boolean.toIntForDB(): Int {
    return if (this) 1 else 0
}

/**
 * @see toIntForDB javadoc
 */
fun Int.toBooleanForDB(): Boolean {
    return this != 0
}
